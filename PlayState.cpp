#include "PlayState.h"
#include "Game.h"

const std::string PlayState::s_playID = "PLAY";

void PlayState::update()
{
    if (TheInputHandler::Instance()->isKeyDown(SDL_SCANCODE_ESCAPE))
    {
        TheGame::Instance()->getStateMachine()->pushState(new PauseState());
    }

    for (auto pCurrentObject : m_gameObjects)
    {
        pCurrentObject->update();
    }
}

void PlayState::render()
{
    for (auto pCurrentObject : m_gameObjects)
    {
        pCurrentObject->draw();
    }
}

bool PlayState::onEnter()
{
    // TODO: Insert pictures of a figures here

    return true;
}

bool PlayState::onExit()
{
    SDL_SetRenderDrawColor(TheGame::Instance()->getRenderer(), 0, 0, 0, 255);

    for (auto pCurrentObject : m_gameObjects)
    {
        pCurrentObject->clean();
    }
    m_gameObjects.clear();

    // TODO: call TheTextureManager::Instance()->clearFrom... function for each picture!

    return true;
}