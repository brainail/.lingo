FROM malyshau/oracle-java-8

MAINTAINER Yahor Malyshau <org.brainail.everboxing@gmail.com>

# Download Android SDK dependencies
RUN dpkg --add-architecture i386 && \
    apt-get update && \
    apt-get install -y libc6:i386 libncurses5:i386 libstdc++6:i386 lib32z1 libbz2-1.0:i386 git unzip && \
    apt-get clean

# Define Android SDK and Gradle environment
ENV ANDROID_SDK_FILE_NAME tools_r25.2.3-linux.zip
ENV GRADLE_VERSION gradle-3.4.1
ENV GRADLE_DIST_FILE_NAME $GRADLE_VERSION-bin.zip
ENV ANDROID_HOME /opt/google/android-sdk-linux
ENV GRADLE_HOME /opt/gradle/$GRADLE_VERSION
ENV PATH ${ANDROID_HOME}/tools:$ANDROID_HOME/platform-tools:$GRADLE_HOME/bin:$PATH

# Download and unpack SDK to ANDROID_HOME directory
RUN mkdir -p $ANDROID_HOME && \
    curl https://dl.google.com/android/repository/$ANDROID_SDK_FILE_NAME --progress-bar --location --output $ANDROID_SDK_FILE_NAME && \
    unzip $ANDROID_SDK_FILE_NAME -d $ANDROID_HOME && \
    rm $ANDROID_SDK_FILE_NAME

# Install Android SDK components
ENV SDK_INSTALL_COMPONENT "echo \"y\" | \"$ANDROID_HOME\"/tools/bin/sdkmanager "
ENV SDK_COMPONENTS_REVISION 2017-04-07-16-20

# Constraint Layout / [Solver for ConstraintLayout 1.0.0-beta4, ConstraintLayout for Android 1.0.0-beta4]
#RUN mkdir -p $ANDROID_HOME/licenses && \
#    echo "8933bad161af4178b1185d1a37fbf41ea5269c55" > $ANDROID_HOME/licenses/android-sdk-license

RUN echo $SDK_COMPONENTS_REVISION && \
    "$ANDROID_HOME"/tools/bin/sdkmanager --update && \
    eval "$SDK_INSTALL_COMPONENT \"tools\"" && \
    eval "$SDK_INSTALL_COMPONENT \"platform-tools\"" && \
    eval "$SDK_INSTALL_COMPONENT \"build-tools;25.0.2\"" && \
    eval "$SDK_INSTALL_COMPONENT \"platforms;android-25\"" && \
    eval "$SDK_INSTALL_COMPONENT \"platforms;android-17\"" && \
    eval "$SDK_INSTALL_COMPONENT \"extras;android;m2repository\"" && \
    eval "$SDK_INSTALL_COMPONENT \"extras;google;m2repository\"" && \
    eval "$SDK_INSTALL_COMPONENT \"extras;m2repository;com;android;support;constraint;constraint-layout;1.0.2\"" && \
    eval "$SDK_INSTALL_COMPONENT \"extras;m2repository;com;android;support;constraint;constraint-layout-solver;1.0.2\""

# Download and unpack Gradle to GRADLE_HOME directory
RUN mkdir -p $GRADLE_HOME && \
    curl https://services.gradle.org/distributions/$GRADLE_DIST_FILE_NAME --progress-bar --location --output $GRADLE_DIST_FILE_NAME && \
    unzip $GRADLE_DIST_FILE_NAME -d /opt/gradle && \
    rm $GRADLE_DIST_FILE_NAME

ENV WORKSPACE /workspace
RUN mkdir -p $WORKSPACE

CMD ["/bin/bash"]
