#include "MainMenuState.h"
#include "TextureManager.h"
#include "Game.h"

const std::string MainMenuState::s_menuID = "MENU";

void MainMenuState::update()
{
    // Loop through our objects and update them
    for (auto pCurrentObject : m_gameObjects) // Range for
    {
        pCurrentObject->update();
    }
}

void MainMenuState::render()
{
    // Loop through our objects and draw them
    for (auto pCurrentObject : m_gameObjects) // Range for
    {
        pCurrentObject->draw();
    }
}

bool MainMenuState::onEnter() // Loading images
{
    if (!TheTextureManager::Instanse()->load("assets/button.png", "playbutton",
                                             TheGame::Instance()->getRenderer()))
        return false;

    if (!TheTextureManager::Instanse()->load("assets/exit.png", "exitbutton", TheGame::Instance()->getRenderer()))
        return false;

    GameObject* button1 = new MenuButton(new LoaderParams(100, 100, 400, 100, "playbutton", 3), s_menuToPlay);
    GameObject* button2 = new MenuButton(new LoaderParams(100, 300, 400, 100, "exitbutton", 3), s_exitFromMenu);

    m_gameObjects.push_back(button1);
    m_gameObjects.push_back(button2);

    return true;
}

bool MainMenuState::onExit() // Deleting images
{
    for (auto pCurrentObject : m_gameObjects)
    {
        pCurrentObject->clean();
    }
    m_gameObjects.clear();

    TheTextureManager::Instanse()->clearFromTextureMap("playbutton");
    TheTextureManager::Instanse()->clearFromTextureMap("exitbutton");

    return true;
}

void MainMenuState::s_menuToPlay()
{
    TheGame::Instance()->getStateMachine()->changeState(new PlayState());
}

void MainMenuState::s_exitFromMenu()
{
    TheGame::Instance()->quit();
}