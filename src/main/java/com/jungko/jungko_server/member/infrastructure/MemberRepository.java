package com.jungko.jungko_server.member.infrastructure;

import com.jungko.jungko_server.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {


	Optional<Member> findByEmail(String email);
}
