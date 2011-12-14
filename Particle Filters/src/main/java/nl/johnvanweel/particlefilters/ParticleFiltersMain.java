package nl.johnvanweel.particlefilters;

import nl.johnvanweel.particlefilters.ui.MainWindow;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: John van Weel
 * Date: 12/12/11
 * Time: 11:12 AM
 *
 */
public class ParticleFiltersMain {
    private static ApplicationContext appCtx;

    public static final void main(String... args) {
        List<String> configs = new LinkedList<String>();
        configs.add("classpath:/META-INF/spring/context.xml");

        appCtx = new ClassPathXmlApplicationContext(configs.toArray(new String[configs.size()]));
    }
}
