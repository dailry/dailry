package com.daily.daily.testutil.config;

import com.daily.daily.common.config.QuerydslConfig;
import com.daily.daily.testutil.generator.DailryGenerator;
import com.daily.daily.testutil.generator.DailryPageGenerator;
import com.daily.daily.testutil.generator.HashtagGenerator;
import com.daily.daily.testutil.generator.MemberGenerator;
import com.daily.daily.testutil.generator.PostCommentGenerator;
import com.daily.daily.testutil.generator.PostGenerator;
import com.daily.daily.testutil.generator.PostLikeGenerator;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({
        QuerydslConfig.class,
        DailryGenerator.class,
        DailryPageGenerator.class,
        MemberGenerator.class,
        PostGenerator.class,
        PostCommentGenerator.class,
        PostLikeGenerator.class,
        HashtagGenerator.class
})
public class JpaTest {

}
