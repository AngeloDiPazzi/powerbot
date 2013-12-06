package org.powerbot.event.debug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.powerbot.client.Client;
import org.powerbot.client.input.Mouse;
import org.powerbot.event.PaintListener;
import org.powerbot.gui.BotChrome;

/**
 * @author Paris
 */
public class ViewMouseTrails implements PaintListener {
	private static final Deque<Point> h = new LinkedList<Point>();

	@Override
	public void repaint(final Graphics g) {
		final Client c = BotChrome.getInstance().getBot().getMethodContext().getClient();
		final Mouse m;
		if (c == null || (m = c.getMouse()) == null) {
			return;
		}

		h.offerFirst(m.getLocation());
		if (h.size() < 3) {
			return;
		}

		final Graphics2D g2 = (Graphics2D) g;
		final double u = 10;
		int i = -1;
		final Iterator<Point> e = h.iterator();
		Point a = e.next();

		while (e.hasNext() && ++i < u) {
			final Point b = e.next();
			g2.setColor(new Color(255, 255, 255, 200 - (int) (i / u * 200d)));
			g2.drawLine(a.x, a.y, b.x, b.y);
			a = b;
		}

		if (i == u) {
			h.pollLast();
		}
	}
}