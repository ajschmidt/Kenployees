FROM centos:7

MAINTAINER ajs@ajs.dorg

RUN	rpm --import /etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-7 && \
		rpm --import https://dl.fedoraproject.org/pub/epel/RPM-GPG-KEY-EPEL-7 && \
		yum -y update ca-certificates && \
		yum -y install \
		epel-release && \
		yum -y clean all

RUN	yum -y install \
		which  \
      curl \
		git \
      java-1.8.0-openjdk \
      java-1.8.0-openjdk-devel \
		rpm-build \
		maven2 && \
		yum -y clean all

WORKDIR /kenployee
ADD * ./
EXPOSE 8080
CMD 	./run.sh

# vi:syntax=Dockerfile
