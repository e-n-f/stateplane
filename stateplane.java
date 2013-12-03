public class stateplane {
	public static class Point {
		double x;
		double y;

		public Point(double sx, double sy) {
			x = sx;
			y = sy;
		}

		public Point() {
		}

		public String toString() {
			return y + "," + x;
		}
	}

	private static Point transform(Point src) {
		Point dest1 = new Point(-122.44598507881165, 37.79862827259064);
		Point dest2 = new Point(-122.40498483181, 37.80382062071036);
		Point dest3 = new Point(-122.4431848526001, 37.78483392804297);
		Point dest4 = new Point(-122.40212559700012, 37.7900145278091);

		Point src1 = new Point(5999457.929564, 2119105.081363998);
		Point src2 = new Point(6011336.550764, 2120756.911064);
		Point src3 = new Point(6000159.931164, 2114064.762164);
		Point src4 = new Point(6012042.266864, 2115718.9621639997);

		double px = src.x;
		double py = src.y;

		// offset to pivot

		px -= src1.x;
		py -= src1.y;

		// convert to polar

		double dist = Math.sqrt(px * px + py * py);
		double angle = Math.atan2(py, px);

		// System.out.println("%% dist " + dist + " angle " + (angle * 180 / Math.PI));

		// derotate to top edge

		double topangle = Math.atan2(src2.y - src1.y, src2.x - src1.x);
		// System.out.println("%% top line angle " + (topangle * 180 / Math.PI));
		angle -= topangle;

		// System.out.println("%% angle wrt top " + (angle * 180 / Math.PI));

		// in new xy coordinate system

		double nx = dist * Math.cos(angle);
		double ny = dist * Math.sin(angle);
		// System.out.println("%% new point wrt top line " + nx + ", " + ny);

		// skew to left edge

		double leftang = Math.atan2(src3.y - src1.y,
					    src3.x - src1.x);
		// System.out.println("%% left side angle " + (leftang * 180 / Math.PI));
		leftang -= Math.atan2(src2.y - src1.y, src2.x - src1.x);
		// System.out.println("%% left side angle wrt top " + (leftang * 180 / Math.PI));

		double lx = src3.x - src1.x;
		double ly = src3.y - src1.y;
		double leftlen = Math.sqrt(lx * lx + ly * ly);
		// System.out.println("%% dist of left side " + leftlen);
		double leftht = Math.sin(leftang) * leftlen;
		double leftwid = Math.cos(leftang) * leftlen;
		// System.out.println("%% triangle " + leftwid + ", " + leftht);

		// System.out.println("%% ny/leftlt " + (ny/leftht));

		nx -= leftwid * (ny / leftht);

		// System.out.println("%% deskew point " + nx + ", " + ny);

		// descale

		nx /= Math.sqrt((src2.x - src1.x) *
				(src2.x - src1.x) +
				(src2.y - src1.y) *
				(src2.y - src1.y));
		ny /= leftht;
	if (false) {
		ny /= Math.sqrt((src3.x - src1.x) *
				(src3.x - src1.x) +
				(src3.y - src1.y) *
				(src3.y - src1.y));
	}

		// rescale

		nx *= Math.sqrt((dest2.x - dest1.x) *
				(dest2.x - dest1.x) +
				(dest2.y - dest1.y) *
				(dest2.y - dest1.y));

		leftang = Math.atan2(dest3.y - dest1.y,
					    dest3.x - dest1.x) -
			  Math.atan2(dest2.y - dest1.y, dest2.x - dest1.x);
		// System.out.println("%% out left side angle " + (leftang * 180 / Math.PI));
		lx = dest3.x - dest1.x;
		ly = dest3.y - dest1.y;
		leftlen = Math.sqrt(lx * lx + ly * ly);
		leftht = Math.sin(leftang) * leftlen;
		leftwid = Math.cos(leftang) * leftlen;

		ny *= leftht;

	if (false) {
		ny *= Math.sqrt((dest3.x - dest1.x) *
				(dest3.x - dest1.x) +
				(dest3.y - dest1.y) *
				(dest3.y - dest1.y));
	}

		// reskew

		// System.out.println("%% rescale point " + nx + ", " + ny);


		nx += leftwid * (ny / leftht);

		// System.out.println("%% reskew point " + nx + ", " + ny);

		// back to polar

		angle = Math.atan2(ny, nx);
		dist = Math.sqrt(nx * nx + ny * ny);

		// System.out.println("%% scaled angle " + (angle * 180 / Math.PI));

		// rerotate

		angle += Math.atan2(dest2.y - dest1.y, dest2.x - dest1.x);

		// System.out.println("%% rerot angle " + (angle * 180 / Math.PI));

		// convert from polar

		Point lp = new Point();

		lp.x = dist * Math.cos(angle);
		lp.y = dist * Math.sin(angle);

		// offset from pivot

		lp.x += dest1.x;
		lp.y += dest1.y;

		return lp;

	}
}
