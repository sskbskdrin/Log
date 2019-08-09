package cn.sskbskdrin.log;

/**
 * A proxy interface to enable additional operations.
 * Contains all possible Log message usages.
 */
public interface LogHelper {

    /**
     * 添加打印者
     *
     * @param printer 打印者
     */
    void addPrinter(Printer printer);

    /**
     * 清除所有打印者
     */
    void clearAdapters();

    /**
     * 设置全局tag
     *
     * @param tag 全局tag
     */
    void tag(String tag);

    /**
     * 打印日志
     *
     * @param priority 优先级
     * @param tag      打印的tag
     * @param message  打印的内容
     * @param obj      异常打印
     */
    void log(int priority, String tag, String message, Object... obj);
}
