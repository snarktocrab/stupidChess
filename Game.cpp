#include "Game.h"

Game* Game::s_pInstance = 0;

bool Game::init(const char* title, int xpos, int ypos, int width, int height, bool fullscren)
{
	int flags = 0;
	if (fullscren)
		flags = SDL_WINDOW_FULLSCREEN;

	if (SDL_Init(SDL_INIT_EVERYTHING) == 0)
	{
		m_pWindow = SDL_CreateWindow(title, xpos, ypos, width, height, flags);

		if (m_pWindow != 0)
		{
			m_pRenderer = SDL_CreateRenderer(m_pWindow, -1, 0);

			if (m_pRenderer != 0)
			{
				SDL_SetRenderDrawColor(m_pRenderer, 0, 0, 0, 255); // Black
			}
			else
				return false;
		}
		else
			return false;
	}
	else
		return false;

	m_bRunning = true;

	return true;
}

void Game::handleEvents()
{
	TheInputHandler::Instance()->update();

	// TODO: Insert here some code to handle events
}

void Game::update()
{
	// TODO: Insert here some update code
}

void Game::render()
{
	SDL_RenderClear(m_pRenderer);

	// TODO: Insert here some draw code

	SDL_RenderPresent(m_pRenderer);
}

void Game::clean()
{
	SDL_DestroyWindow(m_pWindow);
	SDL_DestroyRenderer(m_pRenderer);

	TheInputHandler::Instance()->clean();
	delete TheInputHandler::Instance();

	SDL_Quit();
}

void Game::quit()
{
	m_bRunning = false;
}