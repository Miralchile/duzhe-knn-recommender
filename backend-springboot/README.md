# Spring Boot backend

This module is adapted from the upstream `duzhe-study` backend and extended for the undergraduate recommendation-system project.

The original deployment notes contained internal IP addresses, credentials, and API-key examples. They were intentionally removed before publishing. Configure MySQL, MongoDB, Elasticsearch, and OSS through environment variables or local profile files that are not committed.

Key recommendation-related additions are under `src/main/java/com/duzheStudy/modules/video/`. The backend calls the local FastAPI algorithm service on `127.0.0.1:8082` for KNN recommendations, then maps returned `movieId` values back to video records.
