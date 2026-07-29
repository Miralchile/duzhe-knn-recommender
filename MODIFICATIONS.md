# Modifications from the upstream projects

This repository is not presented as an original full-stack system written entirely from scratch. It is a course/graduation-project adaptation based on:

- `song-yvqi/duzhe-study` for the Spring Boot backend
- `song-yvqi/uniapp-dz` for the uni-app frontend

The following parts are the project-specific work added or substantially adjusted for the KNN recommendation-system graduation project.

## 1. Algorithm service

Added `algorithm-service/recommend_Server.py`, a FastAPI service that:

- reads video metadata from `data.csv`;
- generates `movie.csv` and `rating.csv` when they are absent;
- converts views and likes into normalized implicit scores;
- constructs an item-user rating matrix with pandas;
- trains a cosine-distance KNN model using `sklearn.neighbors.NearestNeighbors`;
- exposes `/set_execution_flag/` for model initialization and `/get_recommendations/` for related-video retrieval.

## 2. Backend recommendation integration

Added the backend video recommendation module under:

```text
backend-springboot/src/main/java/com/duzheStudy/modules/video/
```

This module includes video metadata models, mapper interfaces, mapper XML files, service logic, DTOs, and controllers. It bridges the Java backend with the Python FastAPI service by sending a `movieId` to the local recommendation endpoint and mapping returned IDs back into backend video entities.

Recommendation-related backend integration also appears in:

```text
backend-springboot/src/main/java/com/duzheStudy/modules/worksDisplay/controller/VideoWorkController.java
```

That controller integrates recommended videos into the video feed / display workflow.

## 3. Frontend recommendation integration

Adjusted the uni-app frontend so that recommendation results are visible in the product flow. Relevant files include:

```text
frontend-uniapp/api/videoPlay.js
frontend-uniapp/pages/works/videoPlay.nvue
frontend-uniapp/pages/index/index.vue
frontend-uniapp/pages.json
```

The frontend calls the backend recommendation endpoint, adds a related-video section on the video playback page, and includes recommendation-oriented navigation/display changes.

## 4. Data and thesis artifacts

Added:

```text
algorithm-service/data.csv
docs/thesis.pdf
docs/thesis.docx
```

The data file supports the KNN demonstration pipeline. The thesis files document the undergraduate graduation project.

## 5. Publication cleanup

Before publishing this repository, the following cleanup was performed:

- removed Maven build output (`target/`);
- removed frontend dependency and build output directories (`node_modules/`, `unpackage/`);
- removed nested Git metadata from the copied frontend;
- removed internal deployment instructions from the backend README;
- replaced committed database, MongoDB, Elasticsearch, frontend base URL, and OSS credentials with local/default placeholders;
- replaced hard-coded Aliyun moderation/OSS AccessKey values in Java source with environment-variable based placeholders;
- changed the algorithm service's original absolute `/duzhe/video_data/...` paths to repository-relative paths with environment-variable overrides;
- added a repository-level `.gitignore`.

These publication changes are hygiene changes for GitHub release. They are separate from the original graduation-project implementation work.
