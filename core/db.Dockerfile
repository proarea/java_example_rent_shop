FROM ubuntu:18.04

RUN apt-get update && apt-get install -y postgresql-10

ARG RENTAL_DATABASE_NAME
ARG RENTAL_DB_USERNAME
ARG RENTAL_DB_PASSWORD
ARG RENTAL_DB_MAX_CONNECTIONS

USER postgres
RUN /etc/init.d/postgresql start && /usr/bin/psql --command "ALTER system SET max_connections = $RENTAL_DB_MAX_CONNECTIONS;"  \
    && /usr/bin/psql --command "DROP DATABASE IF EXISTS $RENTAL_DATABASE_NAME;"

RUN    /etc/init.d/postgresql start &&\
    psql --command "CREATE USER $RENTAL_DB_USERNAME WITH SUPERUSER PASSWORD '$RENTAL_DB_PASSWORD';" &&\
    createdb -O $RENTAL_DB_USERNAME $RENTAL_DATABASE_NAME

RUN echo "host all  all    0.0.0.0/0  md5" >> /etc/postgresql/10/main/pg_hba.conf

RUN echo "listen_addresses='*'" >> /etc/postgresql/10/main/postgresql.conf

EXPOSE 5432

VOLUME  ["/etc/postgresql", "/var/log/postgresql", "/var/lib/postgresql"]

CMD ["/usr/lib/postgresql/10/bin/postgres", "-D", "/var/lib/postgresql/10/main", "-c", "config_file=/etc/postgresql/10/main/postgresql.conf"]
