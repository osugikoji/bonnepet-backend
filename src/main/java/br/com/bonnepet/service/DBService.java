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

import java.math.BigDecimal;
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

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private SizeRepository sizeRepository;

    private List<State> stateList;

    private List<City> cityList;

    private List<Address> addressList;

    private List<User> userList;

    private List<Behaviour> behaviourList;

    private List<Size> sizeList;

    public void instantiateDataBase() {
        stateList = createStates();
        cityList = createCities();
        addressList = createAddresses();
        userList = createUsers();
        behaviourList = createBehaviours();
        sizeList = createSizes();
        createPet();
        createHost();
    }

    private void createPet() {
        Pet pet1 = new Pet(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjs6srps1gQqrzujdY5jWx77GtXhiGC5zAHgvWTjhR-GrLTQuS",
                "Tomy",
                "Akita",
                GenderEnum.MALE.name(),
                DateHelper.parseToDate("08/03/2019"),
                PetSizeEnum.MEDIUM.name(),
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                userList.get(0));
        pet1.getBehaviours().addAll(Arrays.asList(behaviourList.get(0), behaviourList.get(1)));

        Pet pet2 = new Pet(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSAu3262iyGDFqBRb_NdSRgn9NGI9M7C1NNzuubwgGWhhfg1ZdoCA",
                "Greeg",
                "Shi-Tzu",
                GenderEnum.FEMALE.name(),
                DateHelper.parseToDate("08/03/2018"),
                PetSizeEnum.SMALL.name(),
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                userList.get(0));
        pet2.getBehaviours().addAll(Arrays.asList(behaviourList.get(0), behaviourList.get(1), behaviourList.get(2)));
        userList.get(0).getPets().addAll(Arrays.asList(pet1, pet2));

        Pet pet3 = new Pet(
                "",
                "Jorge",
                "Shi-Tzu",
                GenderEnum.MALE.name(),
                DateHelper.parseToDate("08/03/2018"),
                PetSizeEnum.SMALL.name(),
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                userList.get(1));
        pet3.getBehaviours().addAll(Arrays.asList(behaviourList.get(0), behaviourList.get(1), behaviourList.get(2)));
        userList.get(1).getPets().add(pet3);

        petRepository.saveAll(Arrays.asList(pet1, pet2, pet3));
        userRepository.saveAll(Arrays.asList(userList.get(0), userList.get(1)));
    }

    private void createHost() {
        Host host1 = new Host(userList.get(0), BigDecimal.valueOf(100), "Tive vários cachorros ao longo dos anos, portanto tenho bastante experiência! Amo a companhia. Tenho disponibilidade para várias atividades e passeios.");
        host1.getPreferenceSizes().addAll(sizeList);
        userList.get(0).setHost(host1);

        Host host2 = new Host(userList.get(1), BigDecimal.valueOf(200), "Pretendo trabalhar de uma meneira que inclua todas as necessidades tanto do seu pet quanto as suas. Caso o seu animalzinho precise de cuidados, não se preocupe que além de ser eficiente sempre o manterei informado.");
        host2.getPreferenceSizes().addAll(Arrays.asList(sizeList.get(0), sizeList.get(1)));
        userList.get(1).setHost(host2);

        hostRepository.saveAll(Arrays.asList(host1, host2));
        userRepository.saveAll(Arrays.asList(userList.get(0), userList.get(1)));
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

    private List<Size> createSizes() {
        Size small = new Size(PetSizeEnum.SMALL.name());
        Size medium = new Size(PetSizeEnum.MEDIUM.name());
        Size large = new Size(PetSizeEnum.LARGE.name());

        List<Size> sizes = new ArrayList<>(Arrays.asList(small, medium, large));
        sizeRepository.saveAll(sizes);

        return sizes;
    }

    private List<User> createUsers() {
        String password = bCryptPasswordEncoder.encode("123");
        User user1 = new User(
                "https://scontent.fcpq4-1.fna.fbcdn.net/v/t1.0-9/47379592_2006731116109305_1469582824796323840_n.jpg?_nc_cat=101&_nc_ht=scontent.fcpq4-1.fna&oh=46926d22a40fa23e99573af91331eaba&oe=5D6F7DDF",
                "koji@gmail.com",
                password,
                "Koji",
                DateHelper.parseToDate("08/03/1997"),
                "19382252031",
                "19963546987",
                addressList.get(0));
        User user2 = new User(
                "https://scontent.fcpq4-1.fna.fbcdn.net/v/t1.0-9/31654_1442854674748_7588399_n.jpg?_nc_cat=109&_nc_ht=scontent.fcpq4-1.fna&oh=9dc88bf534c5ad21d7052966b97c8b65&oe=5D705CE1",
                "zullo@gmail.com",
                password,
                "Joao",
                DateHelper.parseToDate("01/02/1995"),
                "19385557731",
                "19965345698",
                addressList.get(1));
        User user3 = new User(
                "https://scontent.fcpq4-1.fna.fbcdn.net/v/t1.0-1/p160x160/38679668_1849579558460524_8769734777847676928_n.jpg?_nc_cat=110&_nc_ht=scontent.fcpq4-1.fna&oh=ad2d76c3ca5018fac7b48d79f96bdde0&oe=5D7341BA",
                "samuel@gmail.com",
                password,
                "Samuel",
                DateHelper.parseToDate("01/02/1997"),
                "19385557731",
                "19965345698",
                addressList.get(1));
        User user4 = new User(
                "https://scontent.fcpq4-1.fna.fbcdn.net/v/t1.0-9/53089473_2507472172600588_8239496085626683392_n.jpg?_nc_cat=104&_nc_ht=scontent.fcpq4-1.fna&oh=3fbf297780456120f10f9f3a509b2ac4&oe=5D2E9921",
                "josi@gmail.com",
                password,
                "Josi",
                DateHelper.parseToDate("01/02/1992"),
                "19385557731",
                "19965345698",
                addressList.get(1));

        userRepository.saveAll(new ArrayList<>(Arrays.asList(user1, user2, user3, user4)));
        return new ArrayList<>(Arrays.asList(user1, user2, user3, user4));
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