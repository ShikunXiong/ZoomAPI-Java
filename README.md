# ZoomAPI


This is a [Zoom APIs](https://marketplace.zoom.us/docs/api-reference/introduction) wrapper written in Java.
## Contents

- [Background](#background)
- [Install](#install)
- [Usage](#usage)


## Background
This is a class project on [@Crista's](https://github.com/crista) course. Our project has implemented and provided sample codes for all methods in `ChatChannels` and `ChatMessages`. And We have implemented some of APIs in `meeting`, `recording`, `report`, `User` and `Webinar`.

## Install
- `git clone` this project and open it as Maven project.
- Download [Ngrok](https://ngrok.com/download).

## Usage
- Open ngrok and setup a tunnel on port 4040.

    ```
    ngrok http 4040
    ```
- Configure your Zoom credentials in `settings.properties`.
- Run `botm2.java`.
- Copy the `code` in the popped up url and then paste it into terminal.
- If you just want to use `ChatChannels` and `ChatMessages`, you could follow the cmd instructions. But if you want to try other APIS, you should look into the API documents before calling the APIS in our project.
- Project has been set to 10 calls per second.
- If you quit program, you need to turn off the ngrok by `CTRL C` twice and start it again before running the program.

## Credits
[OKHttp](https://square.github.io/okhttp/)

[OAuth Library](https://cwiki.apache.org/confluence/display/OLTU/OAuth+2.0+Client+Quickstart)

[Guava](https://github.com/google/guava)

[Ngrok](https://github.com/dmanchon/ngrok-java-client)


