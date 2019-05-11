package br.com.bonnepet.service;

import br.com.bonnepet.domain.*;
import br.com.bonnepet.domain.enums.BehaviourEnum;
import br.com.bonnepet.domain.enums.GenderEnum;
import br.com.bonnepet.domain.enums.PetSizeEnum;
import br.com.bonnepet.helper.DateHelper;
import br.com.bonnepet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Servico que popula o banco em ambiente de teste ou dev
 */
@Service
public class DBService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private BehaviourRepository behaviourRepository;

    @Autowired
    private PetRepository petRepository;

    private List<State> stateList;

    private List<City> cityList;

    private List<Address> addressList;

    private List<User> userList;

    private List<Behaviour> behaviourList;

    public void instantiateDataBase() {
        stateList = createStates();
        cityList = createCities();
        addressList = createAddresses();
        userList = createUsers();
        behaviourList = createBehaviours();


        Pet pet = new Pet("", "Jorge", "Akita", GenderEnum.MALE.name(),
                DateHelper.parseToDate("08/03/2019"), PetSizeEnum.MEDIUM.name(), "Possui alergia a peixe", userList.get(0));
        pet.getBehaviours().addAll(Arrays.asList(behaviourList.get(0), behaviourList.get(1)));

        petRepository.save(pet);

        userList.get(0).getPets().add(pet);

        userRepository.save(userList.get(0));
    }

    private List<Behaviour> createBehaviours() {
        Behaviour confident = new Behaviour(BehaviourEnum.CONFIDENT.name());
        Behaviour shy = new Behaviour(BehaviourEnum.SHY.name());
        Behaviour aggressive = new Behaviour(BehaviourEnum.AGGRESSIVE.name());
        Behaviour sociable = new Behaviour(BehaviourEnum.SOCIABLE.name());
        Behaviour independent = new Behaviour(BehaviourEnum.INDEPENDENT.name());

        List<Behaviour> behaviours = new ArrayList<>(Arrays.asList(confident, shy, aggressive, sociable, independent));
        behaviourRepository.saveAll(behaviours);

        return behaviours;
    }

    private List<User> createUsers() {
        String password = bCryptPasswordEncoder.encode("123");
        User user1 = new User("", "koji097@gmail.com", password, "Koji", DateHelper.parseToDate("08/03/1997"),
                "19382252031", "19963546987", addressList.get(0));
        User user2 = new User("", "zullo@gmail.com", password, "Joao", DateHelper.parseToDate("01/02/1992"),
                "19385557731", "19965345698", addressList.get(1));

        userRepository.saveAll(new ArrayList<>(Arrays.asList(user1, user2)));
        return new ArrayList<>(Arrays.asList(user1, user2));
    }

    private List<Address> createAddresses() {

        Address address1 = new Address("33343355", "Jardim Recanto", "Jose Bernardinetti", "99", cityList.get(0));
        Address address2 = new Address("35343311", "Barao Geraldo", "Mokarzil", "99", cityList.get(1));
        addressList = new ArrayList<>(Arrays.asList(address1, address2));
        return addressList;
    }

    private List<City> createCities() {
        ArrayList<City> cityList = new ArrayList<>();
        for (State state : stateList) {
            if (state.getName().equals("São Paulo")) {
                City indaiatuba = new City("Indaiatuba", state);
                City campinas = new City("Campinas", state);
                state.getCities().addAll(cityList);
                cityList.addAll(Arrays.asList(indaiatuba, campinas));
            }
            if (state.getName().equals("Minas Gerais")) {
                City divinopolis = new City("Divinopolis", state);
                City beloHorizonte = new City("Belo Horizonte", state);
                state.getCities().addAll(cityList);
                cityList.addAll(Arrays.asList(divinopolis, beloHorizonte));
            }
        }
        cityRepository.saveAll(cityList);
        stateRepository.saveAll(stateList);
        return cityList;
    }

    private List<State> createStates() {
        State sp = new State("São Paulo");
        State mg = new State("Minas Gerais");
        ArrayList<State> stateList = new ArrayList<>(Arrays.asList(sp, mg));
        stateRepository.saveAll(stateList);
        return stateList;
    }
}