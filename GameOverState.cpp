#include "GameOverState.h"
#include "Game.h"

const std::string GameOverState::s_gameOverID = "GAMEOVER";

void GameOverState::update()
{
    for (auto pCurrentObject : m_gameObjects)
    {
        pCurrentObject->update();
    }
}

void GameOverState::render()
{
    for (auto pCurrentObject : m_gameObjects)
    {
        pCurrentObject->draw();
    }
}

bool GameOverState::onEnter() // Loading images
{
    if (!TheTextureManager::Instanse()->load("assets/main.png", "mainbutton", TheGame::Instance()->getRenderer()))
        return false;

    if (!TheTextureManager::Instanse()->load("assets/restart.png", "restartbutton", TheGame::Instance()->getRenderer()))
        return false;

    GameObject* button1 = new MenuButton(new LoaderParams(200, 200, 200, 80, "mainbutton", 3), s_gameOverToMain);
    GameObject* button2 = new MenuButton(new LoaderParams(200, 300, 200, 80, "restartbutton", 3), s_restartPlay);

    m_gameObjects.push_back(button1);
    m_gameObjects.push_back(button2);

    return true;
}

bool GameOverState::onExit() // Deleting images
{
    for (auto pCurrentObject : m_gameObjects)
    {
        pCurrentObject->clean();
    }
    m_gameObjects.clear();

    TheTextureManager::Instanse()->clearFromTextureMap("mainbutton");
    TheTextureManager::Instanse()->clearFromTextureMap("restartbutton");

    return true;
}

void GameOverState::s_gameOverToMain()
{
    TheGame::Instance()->getStateMachine()->changeState(new MainMenuState());
}

void GameOverState::s_restartPlay()
{
    TheGame::Instance()->getStateMachine()->changeState(new PlayState());
}