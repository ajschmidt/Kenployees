FROM centos:7

MAINTAINER ajs@ajs.dorg

# top level of trafficcontrol directory must be mounted as a volume:
# docker run --volume /trafficcontrol:$(pwd) ...
VOLUME /kenployee

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

ENV JAVA_HOME=/usr/lib/jvm/java
WORKDIR /kenployee
#RUN curl -L https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.6.0/apache-maven-3.6.0-bin.tar.gz | tar xzf - && \
    #alternatives --install /usr/bin/mvn mvn /opt/apache-maven*/bin/mvn 1

###

# ADD infrastructure/docker/build/clean_build.sh /
CMD mvn clean install

# vi:syntax=Dockerfile
