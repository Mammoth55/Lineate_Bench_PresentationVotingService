package com.lineate.presentationvotingservice.service;

import com.lineate.presentationvotingservice.entity.Client;
import com.lineate.presentationvotingservice.repository.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepository clientRepository;

    public UserDetailsServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Client client = clientRepository.getByLogin(login);
        if (client == null) {
            throw new UsernameNotFoundException("Client doesn't exists !");
        }
        return SecurityUser.fromClient(client);
    }
}