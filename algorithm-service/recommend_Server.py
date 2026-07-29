from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import pandas as pd
from scipy.sparse import csr_matrix
from sklearn.neighbors import NearestNeighbors
import numpy as np
import os
from pathlib import Path

# 创建 FastAPI 应用
app = FastAPI()

# 定义全局变量
user_movie_table = None
user_movie_table_matrix = None
model_knn = None
title_to_movieid_map = None
execution_flag = {"execute": True}
BASE_DIR = Path(__file__).resolve().parent
DATA_DIR = Path(os.getenv("DUZHE_RECOMMENDER_DATA_DIR", BASE_DIR / "data"))
SOURCE_DATA_PATH = Path(os.getenv("DUZHE_RECOMMENDER_SOURCE_DATA", BASE_DIR / "data.csv"))
MOVIE_CSV_PATH = DATA_DIR / "movie.csv"
RATING_CSV_PATH = DATA_DIR / "rating.csv"

# 定义评分可选值
RATING_SCALE = [0, 0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10]


    
# 生成 评分数据 和 视频元数据 的函数
def generate_csv_files():
    DATA_DIR.mkdir(parents=True, exist_ok=True)
    # 读取 初始数据
    data = pd.read_csv(SOURCE_DATA_PATH)
    
    # 生成 视频元数据
    movie_df = data[["movieId","title", "author", "link"]].copy()
    movie_df.columns = ["movieId","title", "author", "url"]  # 重命名 link 为 url
    movie_df.to_csv(MOVIE_CSV_PATH, index=False)
    
    # 对 views 和 likes 进行对数变换
    data["log_views"] = np.log1p(data["views"])
    data["log_likes"] = np.log1p(data["likes"])
    
    # 归一化 log_views 和 log_likes
    views_min, views_max = np.log1p(532), np.log1p(72000000)
    likes_min, likes_max = np.log1p(15), np.log1p(2100000)
    data["norm_views"] = (data["log_views"] - views_min) / (views_max - views_min)
    data["norm_likes"] = (data["log_likes"] - likes_min) / (likes_max - likes_min)
    
    # 计算综合评分并离散化
    data["score"] = (data["norm_views"] * 0.4 + data["norm_likes"] * 0.6) * 10
    data["rating"] = data["score"].apply(lambda x: min(RATING_SCALE, key=lambda y: abs(y - x)))
    
    # 生成 评分数据
    TOTAL_USERS = 100000  # 固定用户池大小
    rating_data = []
    np.random.seed(42)  # 固定随机种子
    
    # 为每个视频分配评分数量
    for index, row in data.iterrows():
        movie_id = row["movieId"] # 使用原始的字符串 movieId
        num_ratings = min(int(row["views"] / 50000) + 1, 150)  # 评分次数基于 views，最多 150 次
        
        # 从用户池中随机选择 num_ratings 个用户
        user_ids = np.random.choice(range(1, TOTAL_USERS + 1), size=num_ratings, replace=True)
        
        for user_id in user_ids:
            rating = row["rating"]
            rating_idx = RATING_SCALE.index(rating)
            perturb = np.random.randint(-1, 2)  # -1, 0, 1
            new_idx = max(0, min(len(RATING_SCALE) - 1, rating_idx + perturb))
            final_rating = RATING_SCALE[new_idx]
            rating_data.append({"userId": user_id, "movieId": movie_id, "rating": final_rating})
    
    # 转换为 DataFrame 并保存
    rating_df = pd.DataFrame(rating_data)
    rating_df.to_csv(RATING_CSV_PATH, index=False)

def initialize_model():
    global user_movie_table, user_movie_table_matrix, model_knn, title_to_movieid_map
    if not MOVIE_CSV_PATH.exists() or not RATING_CSV_PATH.exists():
        generate_csv_files()
    data_movie = pd.read_csv(MOVIE_CSV_PATH, dtype={"movieId": str})  # 确保 movieId 为字符串
    data_rating = pd.read_csv(RATING_CSV_PATH, dtype={"movieId": str})  # 确保 movieId 为字符串
    movie = data_movie[["movieId", "title"]]
    rating = data_rating[["userId", "movieId", "rating"]]
    data = pd.merge(movie, rating, on="movieId")  # 使用 movieId 合并
    data = data.iloc[:100000, :]
    user_movie_table = data.pivot_table(index=["title"], columns=["userId"], values="rating").fillna(0)
    title_to_movieid_map = movie.set_index("title")["movieId"].to_dict()  # movieId 保持字符串
    user_movie_table_matrix = csr_matrix(user_movie_table.values)
    model_knn = NearestNeighbors(metric='cosine', algorithm='brute')
    model_knn.fit(user_movie_table_matrix)
    
class ExecuteInfo(BaseModel):
    execute: bool

class QueryInfo(BaseModel):
    movie_id: str  # 修改为接受字符串类型的 movieId
@app.post("/set_execution_flag/")
async def set_execution_flag(execute_info: ExecuteInfo):
    execution_flag["execute"] = execute_info.execute
    if execute_info.execute:
        initialize_model()
        return {"message": "Execution enabled and model updated."}
    else:
        return {"message": "Execution disabled. Model will not be updated."}

@app.post("/get_recommendations/")
async def get_recommendations(query_info: QueryInfo):
    global user_movie_table, user_movie_table_matrix, model_knn, title_to_movieid_map
    if user_movie_table is None or user_movie_table_matrix is None or model_knn is None:
        raise HTTPException(status_code=400, detail="Model is not initialized. Please enable execution first.")
    
    # 从 movieId 查找对应的 title
    movie_id = query_info.movie_id
    title = next((t for t, mid in title_to_movieid_map.items() if mid == movie_id), None)
    if title is None:
        raise HTTPException(status_code=404, detail=f"Movie with movieId '{movie_id}' not found.")
    
    # 从 title 查找索引
    try:
        query_index = user_movie_table.index.get_loc(title)
    except KeyError:
        raise HTTPException(status_code=404, detail=f"Movie title '{title}' not found in user_movie_table.")
    
    # 获取推荐
    distances, indices = model_knn.kneighbors(user_movie_table.iloc[query_index, :].values.reshape(1, -1), n_neighbors=8)
    recommendations = []
    for i in range(1, len(distances.flatten())):
        rec_title = user_movie_table.index[indices.flatten()[i]]
        rec_movie_id = title_to_movieid_map.get(rec_title, None)
        if rec_movie_id is not None:
            recommendations.append({"movieID": rec_movie_id})
    
    return {
        "query_movie_id": movie_id,
        "query_movie": title,
        "recommendations": recommendations
    }

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8082)  # 修改为 8082 端口
