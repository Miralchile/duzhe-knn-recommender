# Duzhe KNN Video Recommendation System

本科毕业设计项目：基于协同过滤与 KNN 的个性化视频推荐系统。

## Project structure

```text
backend-springboot/   Spring Boot backend adapted from duzhe-study
frontend-uniapp/      Uni-app frontend adapted from uniapp-dz
algorithm-service/    FastAPI + scikit-learn KNN recommendation service
docs/                 Thesis artifacts
```

## Upstream attribution

This project is built on top of two Gitee open-source projects:

- Backend base: https://gitee.com/song-yvqi/duzhe-study.git
- Frontend base: https://gitee.com/song-yvqi/uniapp-dz.git

The original projects provide the baseline reader/video/community application structure. My undergraduate project adds the recommendation-system layer and integrates it into the existing backend and frontend workflow. See `MODIFICATIONS.md` for the project-specific changes, and `UPSTREAM_DIFF_MAP.md` for the concrete file locations changed relative to the upstream projects.

## Recommendation workflow

The recommendation pipeline has three parts. First, `algorithm-service/recommend_Server.py` prepares video metadata and synthetic rating data from `algorithm-service/data.csv`, builds an item-user matrix, and trains a cosine-distance KNN model with `sklearn.neighbors.NearestNeighbors`. Second, the Spring Boot backend exposes recommendation-related APIs under `modules/video` and calls the FastAPI service on port `8082`. Third, the uni-app frontend requests recommendation results and displays related videos on the video playback page and recommendation tab.

## Local run notes

The repository has been sanitized for public release. Real database, MongoDB, Elasticsearch, and OSS credentials are not included. Configure them through environment variables or local files excluded by `.gitignore`.

Run the algorithm service. It reads `algorithm-service/data.csv` by default and writes generated intermediate files into `algorithm-service/data/`; override those locations with `DUZHE_RECOMMENDER_SOURCE_DATA` and `DUZHE_RECOMMENDER_DATA_DIR` if needed.

```bash
cd algorithm-service
pip install -r requirements.txt
python recommend_Server.py
```

Run the backend from `backend-springboot/` with Maven after preparing MySQL, MongoDB, and Elasticsearch locally. Run the frontend from `frontend-uniapp/` with the uni-app/HBuilderX workflow or your preferred compatible toolchain.
