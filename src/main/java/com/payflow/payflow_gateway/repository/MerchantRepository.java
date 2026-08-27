package com.payflow.payflow_gateway.repository;

import com.payflow.payflow_gateway.domain.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
}