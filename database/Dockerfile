FROM postgres:15.0

ADD pg_hba.conf /var/lib/postgresql/data/
ADD postgresql.conf /var/lib/postgresql/data/
ENV PGDATA /var/lib/postgresql/data/asare
ADD asare_db.sql /docker-entrypoint-initdb.d/

EXPOSE 5432
