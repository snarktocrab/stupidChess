#ifndef _GAME_H_
#define _GAME_H_

#include "InputHandler.h"
#include "LoaderParams.h"
#include "GameObject.h"
#include "TextureManager.h"
#include "GameStateMachine.h"
#include "PlayState.h"
#include "MainMenuState.h"
#include "PauseState.h"

class Game
{
public:
	bool init(const char* title, int xpos, int ypos, int width, int height, bool fullscreen); // Initial function

	void render();
	void update();
	void handleEvents();
	void clean();
	void quit(); // To finish this program

	bool running() { return m_bRunning; }

	SDL_Renderer* getRenderer() const { return m_pRenderer; }

	GameStateMachine* getStateMachine() { return m_pGameStateMachine; }

	static Game* Instance() // This makes this class a singletone, so it has only one member
	{
		if (s_pInstance == 0)
			s_pInstance = new Game();
		return s_pInstance;
	}

private:
	Game() {}

	SDL_Window* m_pWindow = 0;
	SDL_Renderer* m_pRenderer = 0;

	GameStateMachine* m_pGameStateMachine;

	static Game* s_pInstance;

	std::vector<GameObject*> m_gameObjects;

	bool m_bRunning;
};

typedef Game TheGame;

#endif