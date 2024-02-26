package com.example.TehZad;

import com.example.TehZad.project.dto.ProjectDto;
import com.example.TehZad.task.model.Status;
import org.junit.jupiter.api.Assertions;
import com.example.TehZad.project.model.Project;
import com.example.TehZad.project.repository.ProjectRepository;
import com.example.TehZad.project.service.ProjectService;
import com.example.TehZad.user.model.Role;
import com.example.TehZad.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {


    @Mock
    ProjectRepository mockProjectRepository;

    @Test
    public void addProjectTest() {
        ProjectService projectService = new ProjectService(mockProjectRepository);

        ProjectDto projectDto = new ProjectDto();
        projectDto.setName("Project one");
        projectDto.setDescription("Description Project one");

        User creator = new User();
        creator.setUsername("userName");
        creator.setId(1L);
        creator.setRole(Role.MANAGER);


        Mockito.when(mockProjectRepository.save(Mockito.any()))
                .thenAnswer(invocationOnMock -> {
                    Project project = invocationOnMock.getArgument(0, Project.class);
                    project.setId(1L);
                    return project;
                });

        Project project = projectService.addProject(projectDto, creator);

        Assertions.assertEquals(project.getCreator(), creator);
        Assertions.assertEquals(project.getName(), "Project one");
        Assertions.assertEquals(project.getDescription(), "Description Project one");
        Assertions.assertEquals(project.getId(), 1L);
        Assertions.assertEquals(project.getCreated(), LocalDate.now());
        Assertions.assertEquals(project.getStatusChanged(), LocalDate.now());
        Assertions.assertEquals(project.getStatus(), Status.NEW);

        Mockito.verify(mockProjectRepository, Mockito.times(1))
                .save(Mockito.any());

    }

    @Test
    public void updateProjectTest() {
        ProjectService projectService = new ProjectService(mockProjectRepository);

        ProjectDto projectDto = new ProjectDto();
        projectDto.setName("Updated Name");

        User creator = new User();
        creator.setUsername("userName");
        creator.setId(1L);
        creator.setRole(Role.MANAGER);

        Mockito.when(mockProjectRepository.findById(1L))
                .thenAnswer(invocationOnMock -> {
                    Project project = new Project();
                    project.setId(1L);
                    project.setName("Not updated name");
                    project.setDescription("Description");
                    project.setStatus(Status.NEW);
                    project.setCreated(LocalDate.now());
                    project.setStatusChanged(LocalDate.now());

                    project.setCreator(creator);

                    project.setTasks(new ArrayList<>());

                    project.setChildProjects(new ArrayList<>());
                    return Optional.of(project);
                });


        Mockito.when(mockProjectRepository.save(Mockito.any()))
                .thenAnswer(invocationOnMock -> {
                    Project project = invocationOnMock.getArgument(0, Project.class);
                    project.setId(1L);
                    return project;
                });

        Project project = projectService.update(projectDto, 1L);

        Assertions.assertEquals(project.getName(), "Updated Name");
        Assertions.assertEquals(project.getDescription(), "Description");

        Mockito.verify(mockProjectRepository, Mockito.times(1))
                .save(Mockito.any());
        Mockito.verify(mockProjectRepository, Mockito.times(1))
                .findById(1L);

    }


}
