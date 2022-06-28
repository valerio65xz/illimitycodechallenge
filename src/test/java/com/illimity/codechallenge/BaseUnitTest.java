package com.illimity.codechallenge;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public abstract class BaseUnitTest {

    protected static <T> T random(Class<T> randomClass) {
        return new EasyRandom().nextObject(randomClass);
    }

}