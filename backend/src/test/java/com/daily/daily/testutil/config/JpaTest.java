package com.daily.daily.testutil.config;

import com.daily.daily.common.config.QuerydslConfig;
import com.daily.daily.testutil.generator.DailryGenerator;
import com.daily.daily.testutil.generator.DailryPageGenerator;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({
        QuerydslConfig.class,
        DailryGenerator.class,
        DailryPageGenerator.class
})
public class JpaTest {

}
