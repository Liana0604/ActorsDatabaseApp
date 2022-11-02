PRAGMA foreign_keys = ON;

CREATE TABLE "actors"(
    "name"      TEXT NOT NULL UNIQUE PRIMARY KEY,
    "surname"   Text NOT NULL,
    "age"       INTEGER NOT NULL,
    "pet"
    "movie_id"  INTEGER,
    FOREIGN KEY "movie_id" REFERENCES "movies"("id")
        ON UPDATE CASCADE ON DELETE CASCADE,
    );

CREATE TABLE "movies"(
    "id"        INTEGER PRIMARY KEY,
    "name"      TEXT NOT NULL,
    "imdbrate"  FLOAT NOT NULL,
        );

CREATE TABLE "actors_in_movies"(
    "actor_id"	INTEGER NOT NULL,
    "movie_id"	INTEGER NOT NULL,
    "is_active" INTEGER NOT NULL,
    FOREIGN KEY("actor_id") REFERENCES "actors"("id")
         ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY("movie_id") REFERENCES "movies"("id")
         ON UPDATE CASCADE ON DELETE CASCADE,
        UNIQUE("actor_id","movies_id"),
)