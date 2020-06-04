package com.app.core.thread;

class PriorityRunnable implements Runnable {
    private final Priority priority;

    public PriorityRunnable(Priority priority) {
        this.priority = priority;
    }

    @Override
    public void run() {

    }

    public Priority getPriority() {
        return priority;
    }
}
