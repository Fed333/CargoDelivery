FROM alpine

RUN apk add openjdk11 \
    && apk add maven \
    && mkdir cargo-delivery \
    && cd cargo-delivery \
    && mkdir log

VOLUME ./logs
WORKDIR ./cargo-delivery

COPY . ./
COPY startup.sh startup.sh

CMD /bin/sh startup.sh
EXPOSE 8080
