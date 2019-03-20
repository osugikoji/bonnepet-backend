package br.com.lardopet.service;

import br.com.lardopet.domain.User;
import br.com.lardopet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *  Servico que popula o banco em ambiente de teste ou dev
 */
@Service
public class DBService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    public void instantiateDataBase(){
        User user = new User("koji097@gmail.com", bCryptPasswordEncoder.encode("123"), "Koji", 33297165, 982252031);
        userRepository.save(user);
    }
}