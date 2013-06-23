package infrastructure;

import java.util.Stack;

import entities.Entity;

public class TimeData {
	Stack<Frame> frames;
	Frame firstFrame;

	public TimeData() {
		frames = new Stack<Frame>();
		firstFrame = null;
	}

	public Frame getLastFrame() {
		if (!frames.isEmpty())
			return frames.pop();
		else
			return firstFrame;
	}

	public void backUp(GameMap map) {
		Frame frame = new Frame();
		frame.backUp(map);
		addFrame(frame);
	}

	public void restore(GameMap map) {
		Frame frame = getLastFrame();
		map.restore(frame);
	}

	public void addFrame(Frame e) {
		if (frames.size() > 2500) {
			frames.clear();
			firstFrame = e;
		} else {
			frames.push(e);
			if (firstFrame == null) {
				firstFrame = e;
			}
		}
	}
}
