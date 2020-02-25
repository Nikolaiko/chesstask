FROM ubuntu:18.04

LABEL author="nikolai_baklanov"
LABEL maintainer="nikolaiko@mail.ru"
LABEL version="1.0"
LABEL description="Docker image for Jenkins with Android SDK"

ARG DEBIAN_FRONTEND=noninteractive

#ENV TZ=Russia/Omsk
#RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone




RUN dpkg --add-architecture i386

RUN apt-get update && apt-get install -y git \
 wget \
 unzip \
 sudo \
 tzdata \
 locales\
 openjdk-8-jdk \
 libncurses5:i386 \
 libstdc++6:i386 \
 zlib1g:i386



RUN apt-get clean && rm -rf /var/lib/apt/lists /var/cache/apt

RUN locale-gen en_US.UTF-8  
ENV LANG en_US.UTF-8  
ENV LANGUAGE en_US:en  
ENV LC_ALL en_US.UTF-8


ARG android_home_dir=/var/lib/android-sdk/
ARG sdk_tools_zip_file=sdk-tools-linux-4333796.zip
RUN mkdir $android_home_dir
RUN wget https://dl.google.com/android/repository/$sdk_tools_zip_file -P $android_home_dir -nv
RUN unzip $android_home_dir$sdk_tools_zip_file -d $android_home_dir
RUN rm $android_home_dir$sdk_tools_zip_file && chmod 777 -R $android_home_dir


ENV ANDROID_HOME=$android_home_dir
ENV PATH="${PATH}:$android_home_dir/tools/bin:$android_home_dir/platform-tools"
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/


RUN yes | sdkmanager --licenses

ENV JENKINS_HOME=/var/lib/jenkins
RUN mkdir $JENKINS_HOME && chmod 777 $JENKINS_HOME

RUN useradd -m jenkins && echo 'jenkins ALL=(ALL) NOPASSWD:ALL' >> /etc/sudoers
USER jenkins
WORKDIR /home/jenkins

RUN wget http://mirrors.jenkins.io/war-stable/latest/jenkins.war -nv
CMD java -jar jenkins.war

EXPOSE 8080/tcp

