#ifndef _INPUTHANDLER_H
#define _INPUTHANDLER_H

#include <SDL.h>
#include <vector>

#include "Vector2D.h"

enum mouse_buttons
{
	LEFT, MIDDLE, RIGHT
};

class InputHandler
{
public:
	void update();
	void clean();
	void reset();

	bool isKeyDown(SDL_Scancode key);

	bool getMouseButtonState(int buttonNumber)
	{
		return m_mouseButtonStates[buttonNumber];
	}

	Vector2D* getMousePosition()
	{
		return m_mousePosition;
	}

	static InputHandler* Instance()
	{
		if (s_pInstance == 0)
			s_pInstance = new InputHandler();
		return s_pInstance;
	}

private:
	InputHandler();

	std::vector<bool> m_mouseButtonStates;

	Vector2D* m_mousePosition;

	const Uint8* m_keystates;

	static InputHandler* s_pInstance;

	// Private functions to handle different event types

	// Handle keyboard events
	void onKeyDown();
	void onKeyUp();

	// Handle mouse events
	void onMouseMove(SDL_Event &event);
	void onMouseButtonDown(SDL_Event &event);
	void onMouseButtonUp(SDL_Event &event);
};

typedef InputHandler TheInputHandler;

#endif