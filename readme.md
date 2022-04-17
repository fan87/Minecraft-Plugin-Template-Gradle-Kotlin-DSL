
<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->

//DONT_APPLY_TEMPLATE_CHANGE
# Gradle Plugin Template &nbsp;Kotlin DSL Version

> :information_source: This template was planned to support only kotlin, but it **also supports Java**!

> :warning: This template is still in beta! If you have any issue using this template, please let us know!

> :information_source: This is a Developer Note, you don't need to worry about this if you are a texture pack developer.

## Contents

[Gradle Plugin Template &nbsp;Kotlin DSL Version](#gradle-plugin-template-nbspkotlin-dsl-version)
 - [Contents](#contents)
 -  - [Info](#info)
 - [Requirements](#requirements)
 - [Why Gradle?](#why-gradle)
 - [Features](#features)
 -  - [PlaceHolders](#placeholders)
 -  - [Auto Copy + Reload](#auto-copy--reload)
 -  -  - [Auto-Reload Setup](#auto-reload-setup)
 -  -  - [Advanced Setup](#advanced-setup)
 -  -  - [Advanced Setup - Windows](#advanced-setup---windows)
 -  - [NMS Support](#nms-support)
 -  -  - [Setup](#setup)
 -  - [Singleton Support](#singleton-support)
 - [Project Setup](#project-setup)
 - [Project Build](#project-build)
 -  - [Building](#building)
 -  -  - [Building](#building-1)
 -  -  - [Debug/Test Build](#debugtest-build)
 -  - [Output](#output)
 - [Use this template](#use-this-template)


### Info

Template Maintainer:  fan87<br>Template Developer:  fan87

## Requirements

This template is not a template that everyone can use. It requires more skill than Maven Template, and I'm not joking. Most maven users who don't understand Gradle will be very confused since they misunderstand what Gradle is.

If you still want to use this template because you were attracted by features it provides, you can watch [This YouTube Video](https://www.youtube.com/watch?v=5v92GbC9cqo), I think he explains it pretty well and make everything clear.

## Why Gradle?

Watch the video I mentioned above first, then ask again. If you lazy, then here's why:

1. Gradle is using Build Script (`build.gradle`) instead of structured file (`pom.xml`), so you can do anything you want in build script (including shut the computer down) without any plugin.

   ![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/NEMEH168O.png)

   :arrow_up: *`build.gradle.kts`: Gradle allows you to run code in Kotlin or Groovy, execute them, and use return value, so you can do custom behavior without modifying source code of plugins*

2. Gradle is way faster than Maven. If you are experienced, you probably know that `Build Artifact` feature in IntelliJ IDEA is way faster than a maven `mvn package` build. Good new is Gradle is as fast as IntelliJ IDEA's Build Artifact! Bad new is it's way harder than maven, so there are still a lot of people using Maven.

## Features

Here are some features that you didn't expect to get (lol):

### PlaceHolders

You can use and create your own PlaceHolder, so you can put something like CommitID, build date, project version into strings like this:

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/zAA93KXPR.png)

:arrow_up: *Output*

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/nz2gJgcuf.png)

:arrow_up: *Source Code*

They are all calculated while Gradle is building it, so wherever you go the message will always be the same. Here's the compiled code:

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/GfLDOTupV.png)

:arrow_up:*Decompiled code*



### Auto Copy + Reload

> :information_source: You need to enable RCON for this! If you want to change the host, port, or password, please check the `build.gradle.kts`

You can now reload the plugin automatically every time you build!

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/mPdRsI2_3.png)

#### Auto-Reload Setup

To set Auto Reload up, you need to do these following things:

1. Go to your `server.properties`, enable RCON, change the password to non-default value

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/XY5oOcWjg.png)

2. Now go to your run configuration (See also: [Debug/Test Build](#debugtest-build)), and add `SERVER_DIR` environment variable:

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/BppoyPd4K.png)

#### Advanced Setup

If you want to use plugins like [PlugMan X](https://www.spigotmc.org/resources/plugmanx.88135/) to reload your plugin, you can modify the command , or create a new task. (There will be example in `build.gradle.kts`, I added them after taking this screenshot and I'm lazy to do it again lol)

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/BnBj4BM0y.png)

#### Advanced Setup - Windows

> :information_source: This is not required, and it happens randomly, so it doesn't matter if you are lazy to do it.

> :warning: [PlugMan X](https://www.spigotmc.org/resources/plugmanx.88135/) or PlugMan (Unrecommended) is required!

On windows, if the file is not closed yet, it will lock the file so other program don't have access to write it. To deal with that, you probably need to run some new tasks: `unloadPlugin` and `loadPlugin`, execute it before `copyPlugin`, and then put `loadPlugin` after `copyPlugin`.

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/f2RJ7osBD.png)

### NMS Support

> :information_source: This is disabled by default! If you want to enable it, change `enableNMS` to true

> :man_scientist: This feature is in experimental state. If you got any issue using this feature, please let us know!

> :warning: This only supports 1.17 or above for now. If you want to have 1.17- NMS support, please change the dependency directly to `org.spigotmc:spigot:<version>`!

> :information_source: This is basically same as Spigot's [Special Source Plugin](https://www.spigotmc.org/threads/spigot-bungeecord-1-17-1-17-1.510208/#post-4184317) but for Gradle. So if you want more information about it, please check [me](https://www.spigotmc.org/threads/spigot-bungeecord-1-17-1-17-1.510208/#post-4184317)

You can call NMS methods with remapped name directly, so you can do stuff like send packet etc.

#### Setup

1. Download build tool
2. Run it:

```sh
java -jar BuildTool.jar --rev <minecraft version> --remapped
```

3. Now, change the `enableNMS` to true (in your `build.gradle.kts`)

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/7KZPeL_Xm.png)

4. Now, change the building command by inserting `remap` after `applyPlaceHolder`. **OUTDATED**

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/dkphW12nR.png)

### Singleton Support

> :man_scientist: This feature is in experimental state. If you got any issue using this feature, please let us know!

> :warning: This feature is Kotlin only! It won't work in Java!

You can finally stop using `MainClass.INSTANCE`! With Kotlin, `MainClass` will be converted into `MainClass.INSTANCE`

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/Er8YLZ9rC.png)

-----

## Project Setup

> :information_source: This is not a Kotlin, Gradle or Java tutorial. I won't be explaining everything because there are too many things that can happen while you doing them, even if you are experienced using Google, keep in mind that there aren't many resources out there on it since Gradle community is way smaller than Maven's.

1. Load `gradle.build.kts` as Gradle Project
2. Normally, it will fail with `Unsupported class version`. But **don't worry**.
3. Go to the Gradle Settings

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/jWReLRy9Z.png)

4. Select Java 11 or above (`Gradle JVM` Option), then click on OK

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/Eva6n4fui.png)

5. Choose the language you want, you can use Kotlin or Java, then delete one of those two folders. You can also keep 2 of them if you want to. If you use Java, **REMEMBER TO REMOVE `dependsOn("kotlinSingleton")`**

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/RC3YpFyHd.png)

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/suUbn9X1B.png)

6. Now, decide if you are going to use NMS, and what the Minecraft version will be, and modify the `build.gradle.kts`:

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/nk3-fBmg_.png)

7. After that, you can start adding your own place holders. There are some examples in those two main classes, and `build.gradle.kts`:

`Main.kt`: Example usage. It also works with Java. Keep in mind that you can't split them to make them something like `"%GradlePluginTemplate.PlaceHolder" + ".CommitID%"`, it **won't** work

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/Yg8nwJfQI.png)

`build.gradle.kts`: This registers some placeholders like `GradlePluginTemplate.PlaceHolder.Version`. Note that `GradlePluginTemplate` will be replaced by GitHub to your repository name.

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/Q8SWgdwvc.png)



## Project Build

### Building

> :warning:  This is still in Experimental state, if anything went wrong, please report the issue to the template maintainer or developer.

#### Building

You can use `debug` to do autorealod

#### Debug/Test Build

> :warning: You have to follow the instruction @ [Auto Reload Setup](#auto-reload-setup)

If you want to do debug it (like Auto Reload and stuff), run `debug` instead. You also need to put environment variable `SERVER_DIR` in (as the root directory of your server). You also have to follow instructions @  [Auto Reload Setup](#auto-reload-setup) in order to use it.

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/UZIe7YIlY.png)

### Output

> :warning: If you have enabled NMS, the final output will be `project-name-1.0-SNAPSHOT-remapped.jar`

(I know there are some people who don't use Gradle out there being confused why isn't there `target` directory.)

The output jar is located at `build/libs/`, and if you want to have version with working placeholders, choose the one with `placeholder-applied` prefix:

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/esDEuctnk.png)

(3 AM if you are wondering what is `3:0` in the background lol)



## Use this template

> :warning: Normally, you don't have to do this, so don't worry about it

> :warning: **IN THIS VERSION, IT'S REQUIRED! YOU HAVE TO DO THIS SINCE GITHUB ACTIONS WERE NOT DONE YET!**

If you are on GitHub, and you want to use this template:

1. Click on `Use this template`

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/Fm8GRNNAK.png)

2. Now, create a repository using that template

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/4KZzlUGsS.png)

> :warning: Please use the same naming method for your Repository Name! (replace ` ` with `-`, and first letter of words are caps)

3. Wait for it...

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/kahBCwm3u.png)

4. Now, you should see this:

![image-20220408113411381](/home/fan87/.config/Typora/typora-user-images/image-20220408113411381.png)

5. Clone the repository, and CD into it:

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/3A6CFTEDK.png)

6. Run `apply_template.py`, and use the repository name as argument

![Responsive image](https://storage.gato.host/61068f9c11c02e002297ebf2/AADsyK5pP.png)

7. Commit, and push:

```shell
git add .
git commit -m "Initialized Project"
git push
```

8. Start following the instructions at [Project Setup](#project-setup)



<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->


<!-- You shouldn't be editing this file! Edit `readme.raw.md` instead! -->
