# Upstream diff map

This file marks the main changed locations relative to the two upstream Gitee projects:

- Backend upstream: `https://gitee.com/song-yvqi/duzhe-study.git`
- Frontend upstream: `https://gitee.com/song-yvqi/uniapp-dz.git`

The comparison was checked against freshly cloned upstream repositories on 2026-07-29. The map below focuses on recommendation-system work and publication cleanup. Large framework-level or formatting differences are not claimed as core project contributions unless they directly support recommendation behavior.

## 1. Files/directories added for the KNN recommendation project

| Path in this repository | Upstream status | What changed |
| --- | --- | --- |
| `algorithm-service/recommend_Server.py` | Absent from both upstream projects | Added FastAPI service that builds the KNN recommender with pandas, scipy sparse matrix, and `sklearn.neighbors.NearestNeighbors`. |
| `algorithm-service/data.csv` | Absent from both upstream projects | Added video metadata / interaction-source data used by the recommendation pipeline. |
| `algorithm-service/requirements.txt` | Absent from both upstream projects | Added Python dependency list for the KNN service. |
| `backend-springboot/src/main/java/com/duzheStudy/modules/video/` | Absent from `duzhe-study` | Added backend recommendation module: models, DTOs, mappers, services, controllers, and MongoDB repository. |
| `docs/thesis.pdf` and `docs/thesis.docx` | Absent from both upstream projects | Added undergraduate thesis artifacts. |
| `MODIFICATIONS.md` | Absent from both upstream projects | Added attribution and project-specific modification summary. |

## 2. Backend recommendation integration

### `backend-springboot/src/main/java/com/duzheStudy/modules/video/`

This whole directory is a project-specific addition relative to `duzhe-study`. Important files:

| File | Relevant lines | Role |
| --- | ---: | --- |
| `controller/MovieInfoController.java` | whole file | Exposes backend API for recommendation lookup by `movieId`. |
| `controller/UserMovieInfoController.java` | whole file | Exposes user-video score read/write APIs. |
| `service/impl/MovieInfoServiceImpl.java` | 60-80 | Calls the Python FastAPI KNN endpoint `/get_recommendations/`, handles fallback recommendations, and maps recommended IDs back to MongoDB video records. |
| `service/impl/MovieInfoServiceImpl.java` | 82-90 | Adds fallback random-video logic when the KNN service returns fewer than 8 items. |
| `service/impl/DealUserMovieInfoService.java` | 34-70 | Updates user rating data and triggers model refresh through `/set_execution_flag/`. |
| `domain/UserMovieInfo.java` | whole file | Adds user-video interaction model used by scoring and recommendation updates. |
| `domain/MovieInfo.java` | whole file | Adds video metadata model used by the recommendation module. |
| `domain/dto/AlgResDTO.java`, `MovieDTO.java`, `MovieInfoCsvDTO.java` | whole files | Adds DTOs for Python service response and CSV import/export. |
| `mapper/UserMovieInfoMapper.java` and `mapper/UserMovieInfoMapper.xml` | whole files | Adds score insertion, lookup, update, and recent-interaction queries. |
| `mapper/MovieInfoMapper.java` and `mapper/MovieInfoMapper.xml` | whole files | Adds batch lookup for recommended video IDs. |

### `backend-springboot/src/main/java/com/duzheStudy/modules/worksDisplay/controller/VideoWorkController.java`

The upstream `duzhe-study` version has a simpler `/video/page` implementation. This version changes the feed logic to mix random seed videos with recommendation results:

| Relevant lines | What changed |
| ---: | --- |
| 137 | Publication cleanup: replaced a hard-coded public host with `localhost`. |
| 147-173 | Reworked `/video/page` for page 1: choose a random first video, call `movieInfoService.getRecommendation(randomMovieId)`, convert returned `MovieInfo` records into `VideoVo`, and append up to 7 recommended videos. |
| 175-200 | Reworked subsequent pages: use a video from the previous page as the recommendation base, then append recommended videos. |
| 202-215 | Adds a random tail item and caps the response to `PAGE_SIZE`. |

## 3. Frontend recommendation integration

### `frontend-uniapp/api/videoPlay.js`

The upstream `uniapp-dz` version called hard-coded public URLs and did not expose the KNN recommendation endpoint. This version changes the API layer:

| Relevant lines | What changed |
| ---: | --- |
| 6-47, 64-76, 85-110 | Publication cleanup and deployment portability: replaced hard-coded public URLs with relative backend routes. |
| 49-55 | Added `addUserMovieInfo(data)` for writing user-video scores. |
| 56-62 | Added `readScore(params)` for reading a user's score on a video. |
| 78-83 | Added `getNewVideoWorkById(id)` to call `/duzhe/system/movieInfo/getRecommendation?movieId=...`. |

### `frontend-uniapp/pages/works/videoPlay.nvue`

The playback page is the main visible frontend integration point:

| Relevant lines | What changed |
| ---: | --- |
| 403-560 | Adds/marks the related-recommendation tab content and card list rendering for returned videos/images. |
| 573-584 | Imports the recommendation and scoring APIs: `addUserMovieInfo`, `readScore`, and `getNewVideoWorkById`. |
| 667-692 | On page load, calls the new recommendation endpoint and normalizes returned items for frontend display, including duration formatting and cover image URL refresh. |
| 757-767 | Reads the current user's existing score for the current video. |
| 770-790 | Writes a changed score to the backend with `addUserMovieInfo`, which then contributes to recommendation data updates. |

### Other frontend locations

| File | What changed |
| --- | --- |
| `frontend-uniapp/pages.json` | Adds/adjusts navigation for the recommendation-facing flow. |
| `frontend-uniapp/pages/index/index.vue` | Adjusts home tabs/display around `热门` / `推荐` categories. |
| `frontend-uniapp/config.js`, `frontend-uniapp/utils/request.js`, `frontend-uniapp/App.vue` | Publication cleanup: replaces machine-specific public backend hosts with `localhost`. |

## 4. Algorithm service details

`algorithm-service/recommend_Server.py` is the clearest standalone implementation of the KNN portion:

| Section | What it does |
| --- | --- |
| Global paths and config | Reads `data.csv` from the repository by default and writes generated `movie.csv` / `rating.csv` under `algorithm-service/data/`. |
| `generate_csv_files()` | Converts raw video metadata into video metadata and implicit rating data. Views and likes are log-normalized and converted into a 0-10 rating scale. |
| `initialize_model()` | Builds a video-user pivot table, converts it to a sparse matrix, and fits a cosine-distance `NearestNeighbors` KNN model. |
| `/set_execution_flag/` | Allows the backend to refresh the model after user interaction data changes. |
| `/get_recommendations/` | Receives a `movie_id`, finds its row in the pivot table, returns nearest-neighbor video IDs. |

## 5. Publication cleanup relative to upstream/local project state

These changes were made for safe GitHub publication and should be understood as repository hygiene, not as core graduation-project algorithm work:

| Location | Cleanup |
| --- | --- |
| `.gitignore` | Excludes build output, dependency folders, local env files, logs, Python caches. |
| `backend-springboot/README.md` | Removed internal deployment commands, IP addresses, Redis password, and API-key examples from the original local README. |
| `backend-springboot/src/main/resources/application*.yml` | Replaced committed database, MongoDB, Elasticsearch, and OSS values with environment-variable placeholders. |
| `backend-springboot/src/main/java/com/duzheStudy/modules/common/content/ScanText.java` and `ScanImage.java` | Replaced hard-coded Aliyun moderation AccessKey values with environment variables. |
| `backend-springboot/src/main/java/com/duzheStudy/common/utils/OSSUtils.java` | Replaced hard-coded OSS AccessKey values with environment variables. |
| `frontend-uniapp/config.js`, `frontend-uniapp/utils/request.js`, `frontend-uniapp/App.vue` | Replaced hard-coded backend hosts with `localhost`. |

## 6. Attribution boundary

The base application, general reader/community/video scaffolding, many UI components, and most generic backend infrastructure come from the two upstream projects. The project-specific contribution is the recommendation layer: algorithm service, video recommendation data model, backend bridge, recommendation API exposure, score capture, and frontend related-video display.
