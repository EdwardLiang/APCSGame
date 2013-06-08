package infrastructure;

import java.util.Stack;
import java.util.LinkedList;

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

	public void addFrame(Frame e) {
		frames.push(e);
		if (firstFrame == null) {
			firstFrame = e;
		}
	}
}
