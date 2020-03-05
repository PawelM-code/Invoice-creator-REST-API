package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.item.Item;
import com.app.invoicecreator.domain.owner.Owner;
import com.app.invoicecreator.domain.owner.OwnerDto;
import com.app.invoicecreator.mapper.OwnerMapper;
import com.app.invoicecreator.service.OwnerService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OwnerController.class)
public class OwnerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @MockBean
    private OwnerMapper ownerMapper;

    @Mock
    private Owner owner;

    @Mock
    private OwnerDto ownerDto;

    @Mock
    private OwnerDto ownerDto2;

    @Before
    public void initSetup() {
        //Given
        owner = new Owner(
                1L,
                "MyCompany",
                111L,
                22222L,
                "ul. Calineczki",
                "12-1212-1212-1212",
                "test@test.test");
        ownerDto = new OwnerDto(
                1L,
                "MyCompany",
                111L,
                22222L,
                "ul. Calineczki",
                "12-1212-1212-1212",
                "test@test.test");
        ownerDto2 = new OwnerDto(
                1L,
                "MyCompany2",
                111L,
                22222L,
                "ul. Calineczki 2",
                "12-1212-1212-1212",
                "test@test.test");
    }

    @Test
    public void testSaveOwner() throws Exception {
        //Given
        when(ownerMapper.mapToOwner(any())).thenReturn(owner);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(ownerDto);

        //When & Then
        mockMvc.perform(post("/v1/owner")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andDo(print());

        verify(ownerService, times(1)).save(owner);
    }

    @Test
    public void testUpdateOwner() throws Exception {
        //Given
        when(ownerMapper.mapToOwnerDto(any())).thenReturn(ownerDto2);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(ownerDto2);

        //When & Then
        mockMvc.perform(put("/v1/owner")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.workingAddress", is("ul. Calineczki 2")))
                .andExpect(jsonPath("$.bankAccount", is("12-1212-1212-1212")))
                .andExpect(jsonPath("$.name", is("MyCompany2")));
    }

    @Test
    public void testGetOwner() throws Exception {
        //Given
        when(ownerMapper.mapToOwnerDto(any())).thenReturn(ownerDto);
        when(ownerService.findById(anyLong())).thenReturn(Optional.of(owner));

        //When & Then
        mockMvc.perform(get("/v1/owner/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.workingAddress", is("ul. Calineczki")))
                .andExpect(jsonPath("$.email", is("test@test.test")))
                .andExpect(jsonPath("$.name", is("MyCompany")));
    }

    @Test
    public void testGetItems() throws Exception {
        //Given
        List<OwnerDto> ownerDtoList = new ArrayList<>();
        ownerDtoList.add(ownerDto);
        List<Owner> ownerList = new ArrayList<>();
        ownerList.add(owner);

        when(ownerService.getOwners()).thenReturn(ownerList);
        when(ownerMapper.mapToOwnerDtoList(any())).thenReturn(ownerDtoList);

        //When & Then
        mockMvc.perform(get("/v1/owner")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("MyCompany")))
                .andExpect(jsonPath("$[0].nip", is(111)))
                .andExpect(jsonPath("$[0].regon", is(22222)))
                .andExpect(jsonPath("$[0].workingAddress", is("ul. Calineczki")))
                .andExpect(jsonPath("$[0].bankAccount", is("12-1212-1212-1212")))
                .andExpect(jsonPath("$[0].email", is("test@test.test")))
                .andDo(print());
    }
}
