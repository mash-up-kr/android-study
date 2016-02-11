```
스터디 진행 : 2016년 2월 6일, 김동희
최조 작성자 : 고민규
최초 작성일 : 2016년 2월 6일
마지막 수정 : 2016년 2월 7일, 고민규
```

#  0강 - 안드로이드 개발 환경 구축하기

## 들어가면서

Mash-Up 안드로이드팀에서는 안드로이드 개발을 위해 Android Studio를 사용중에있다.


안드로이드 개발팀의 첫 스터디인 이번 시간에는 Java 개발을 위한 환경을 구축하고,
Android Studio를 설치하여'Hello World' 를 출력하는 앱을 빌드하는 과정까지 진행한다.


## Java 개발 환경 구축

Android Application을 개발 할 수 있는 IDE인 Android Studio는, Java JVM 위에서 돌아가는 소프트웨어이다.


따라서 Android Studio를 설치하여 이용하기 위해서는, Java 환경이 사용하고자 하는 컴퓨터에 준비되어 있어야한다.

### JDK 설치

[링크](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)를 클릭하여 JDK (Java SE Development Kit) 을 다운받는 페이지로 이동한다.

링크로 이동해보면, Java SE 7의 마지막 public update라는 문구와 JDK에 대한 설명, 몇 가지 부가적인 메세지들과 함께 Java 환경을 구축하고자 하는 OS 별 다운로드 링크가 준비되어있다.

이 문서가 작성된 시간을 기준으로 자세히 읽어보면, JDK 7u79와 JDK 7u80이 동시에 제공되고 있음을 알 수 있다.
두 버젼의 차이점은 링크에 나와있는대로, 7u79의 경우 Java 7 의 크리티컬 보안 패치가 된 버젼이고, 7u80의 경우 7u79 이후의 보안 패치들이 적용 된 버젼이다.

JDK 7u79 버젼을 사용하면서 발생하는 몇 가지 보안 이슈에 노출 되고있는 상황이 아니라면, 굳이 7u80 버젼을 쓸 필요는 없다.
자세한 사항은 [Java 7 릴리즈 노트](http://www.oracle.com/technetwork/java/javaseproducts/documentation/javase7supportreleasenotes-1601161.html)를 확인하면 알 수 있다. (들어가서 확인해보면 알겠지만, 7u97 버젼까지 업데이트 된 것을 확인 할 수 있다.)

설치 할 JDK의 버젼과 올바른 운영체제의 아키텍쳐까지 다 선택했다면, 라이센스 동의 후 해당하는 설치 파일 링크를 클릭하여 설치 파일을 받는다.

JDK 설치는 설치 프로그램에서 진행하는 순서에 맞게 설치하면 된다.


### 환경 변수 등록

자바를 설치 한 후에 환경 변수를 등록해줘야 `java` 나 `javac` 과 같은 명령어를 사용 할 수 있다.
Android Studio의 경우 Java 절대 경로를 찾아 직접 Java와 연결되지만, 일반적으로 Java를 설치하면 `JAVA_HOME` 이라는 환경 변수를 추가한다.

#### Windows 에서의 환경 변수 추가

윈도우에서 notepad를 열어 다음의 내용을 복사한다.

```bat
reg add "HKLM\SYSTEM\CurrentControlSet\Control\Session Manager\Environment" /v JAVA_HOME /d "Java를 설치 한 경로"
reg add "HKLM\SYSTEM\CurrentControlSet\Control\Session Manager\Environment" /v Path /d "%Path%;%%JAVA_HOME%%\bin\"
```

위 내용 중 `Java를 설치 한 경로` 라고 써있는 부분을 Java가 설치 된 경로로 바꿔주면 된다.

7u79 버젼을 받은 경우 경로는 `C:\Program Files\Java\jdk1.7.0_79`, 7u80 버젼을 받은 경우 경로는 `C:\Program Files\Java\jdk1.7.0_80` 일 것이다.

그리고 메모장에서 파일을 원하는 곳에 `java_env_registry.bat` 라고 저장한 후, 저장한 파일을 실행한다.

마지막으로 Windows의 `cmd`에서 `java -version` 을 실행했을 때 본인이 설치한 Java 버젼이 나온다면 성공 한 것이다.

#### OSX 에서의 환경 변수 추가

Mac OSX의 경우, 사용자 루트 폴더에 있는 .bash_profile 파일에 환경 변수를 등록한다.

Terminal 앱에서 다음과 같이 실행한다.

```sh
$ vim .bash_profile 

export JAVA_HOME=$(Java를 설치한 경로)

$ source .bash_profile

$ echo $JAVA_HOME
```

`Java를 설치한 경로`에 Java가 설치 된 경로를 입력한다.
7u79 버젼을 설치한 경우 `/Library/Java/JavaVirtualMachines/jdk1.7.0_79.jdk/Contents/Home/`, 7u80 버젼을 설치한 경우 `/Library/Java/JavaVirtualMachines/jdk1.7.0_80.jdk/Contents/Home/` 을 입력하면 된다.

마지막 `$ echo $JAVA_HOME` 을 실행하고 올바른 경로가 출력 된 경우,
`$ java -version` 을 실행하여 설치한 Java의 버젼이 출력되면 성공한 것이다.


## Android Studio 설치


<a rel="license" href="http://creativecommons.org/licenses/by/4.0/"><img alt="크리에이티브 커먼즈 라이선스" style="border-width:0" src="https://i.creativecommons.org/l/by/4.0/88x31.png" /></a><br />이 저작물은 <a rel="license" href="http://creativecommons.org/licenses/by/4.0/">크리에이티브 커먼즈 저작자표시 4.0 국제 라이선스</a>에 따라 이용할 수 있습니다.
