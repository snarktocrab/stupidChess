#include "PauseState.h"
#include "Game.h"

const std::string PauseState::s_pauseID = "PAUSE";

void PauseState::s_pauseToMain()
{
    TheGame::Instance()->getStateMachine()->changeState(new MainMenuState());
}

void PauseState::s_resumePlay()
{
    TheGame::Instance()->getStateMachine()->popState();
}

void PauseState::update()
{
    for (auto pCurrentObject : m_gameObjects) // Range for
    {
        pCurrentObject->update();
    }
}

void PauseState::render()
{
    for (auto pCurrentObject : m_gameObjects)
    {
        pCurrentObject->draw();
    }
}

bool PauseState::onEnter() // Loading images
{
    if (!TheTextureManager::Instanse()->load("assets/resume.png", "resumebutton", TheGame::Instance()->getRenderer()))
        return false;

    if (!TheTextureManager::Instanse()->load("assets/main.png", "mainbutton", TheGame::Instance()->getRenderer()))
        return false;

    GameObject* button1 = new MenuButton(new LoaderParams(200, 100, 200, 80, "mainbutton", 3), s_pauseToMain);
    GameObject* button2 = new MenuButton(new LoaderParams(200, 300, 200, 80, "resumebutton", 3), s_resumePlay);

    m_gameObjects.push_back(button1);
    m_gameObjects.push_back(button2);

    return true;
}

bool PauseState::onExit() // Deleting images
{
    for (auto pCurrentObject : m_gameObjects)
    {
        pCurrentObject->clean();
    }
    m_gameObjects.clear();

    TheTextureManager::Instanse()->clearFromTextureMap("mainbutton");
    TheTextureManager::Instanse()->clearFromTextureMap("resumebutton");

    // Reset mouse button states to false
    TheInputHandler::Instance()->reset();

    return true;
}