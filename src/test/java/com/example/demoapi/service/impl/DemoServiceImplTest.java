package com.example.demoapi.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.example.demoapi.exception.DemoNotFoundException;
import com.example.demoapi.model.domain.Demo;
import com.example.demoapi.model.entity.DemoEntity;
import com.example.demoapi.repository.DemoRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Class: DemoServiceImplTest.
 * @version 1.0.0
 * @author Relari
 */

class DemoServiceImplTest {

    @Mock
    private DemoRepository demoRepository;

    @InjectMocks
    private DemoServiceImpl demoService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save() {

        var demoEntity = new DemoEntity(1, "Demo 1");

        when(demoRepository.save(any()))
                .thenReturn(demoEntity);

        var demo = new Demo(1, "Demo 1");

        demoService.save(demo);

        assertEquals(demo.getId(), demoEntity.getId());
        assertEquals(demo.getDescription(), demoEntity.getDescription());

    }

    @Test
    void getDemos() {

        var demoEntity = new DemoEntity(1, "Demo 1");

        when(demoRepository.findAll())
                .thenReturn(Collections.singletonList(demoEntity));

        var demo = new Demo(1, "Demo 1");

        var demos = demoService.getDemos();

        assertEquals(demo.getId(), demos.get(0).getId());
        assertEquals(demo.getDescription(), demos.get(0).getDescription());

    }

    @Test
    void findDemo() {

        var demoEntity = new DemoEntity(1, "Demo 1");

        when(demoRepository.findById(anyInt()))
                .thenReturn(Optional.of(demoEntity));

        var demo = demoService.findDemo(1);

        assertEquals(demo.getId(), demoEntity.getId());
        assertEquals(demo.getDescription(), demoEntity.getDescription());

    }

    @Test
    void findDemoError() {

        when(demoRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> demoService.findDemo(1)
        );

    }

    @Test
    void deleteDemo() {

        var demoEntity = new DemoEntity(1, "Demo 1");

        when(demoRepository.findById(anyInt()))
                .thenReturn(Optional.of(demoEntity));

        demoRepository.deleteById(anyInt());

        demoService.deleteDemo(1);

        assertNotNull(demoEntity);

    }

    @Test
    void deleteDemoError() {

        when(demoRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(
                DemoNotFoundException.class,
                () -> demoService.deleteDemo(1)
        );
    }
}