cmake_minimum_required(VERSION 3.6)
project(StupidChess)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES
        main.cpp
        Game.cpp
        GameStateMachine.cpp
        InputHandler.cpp
        SDLGameObject.cpp
        TextureManager.cpp
        MenuButton.cpp
        MainMenuState.cpp
        PlayState.cpp
        PauseState.cpp GameOverState.cpp)


add_executable(StupidChess ${SOURCE_FILES}
        "Game.h"
        "GameObject.h"
        "GameState.h"
        "GameStateMachine.h"
        "InputHandler.h"
        "LoaderParams.h"
        "SDLGameObject.h"
        "TextureManager.h"
        "Vector2D.h"
        MenuButton.h
        MenuState.h
        MainMenuState.h
        PlayState.h
        PauseState.h GameOverState.h)

INCLUDE(FindPkgConfig)

PKG_SEARCH_MODULE(SDL2 REQUIRED sdl2)
PKG_SEARCH_MODULE(SDL2IMAGE REQUIRED SDL2_image)

INCLUDE_DIRECTORIES(${SDL2_INCLUDE_DIRS} ${SDL2IMAGE_INCLUDE_DIRS})
TARGET_LINK_LIBRARIES(${PROJECT_NAME} ${SDL2_LIBRARIES} ${SDL2IMAGE_LIBRARIES})

#find_library(SDL2_IMAGE_LIBRARY NAME SDL2_Image)