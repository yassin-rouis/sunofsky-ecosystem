package net.joueuranonyme.sunofsky.core.schedules;

public class Schedules {
	public static void init() {
		BossBarSchedule.init();
	}
	
	public static void schedule() {
		BossBarSchedule.schedule();
	}
	public static void end() {
		BossBarSchedule.unschedule();
	}
}