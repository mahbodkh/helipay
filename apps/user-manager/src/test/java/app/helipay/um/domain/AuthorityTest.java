package app.helipay.um.domain;

import static org.assertj.core.api.Assertions.assertThat;

import app.helipay.um.utils.TestUtil;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class AuthorityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Authority.class);
        Authority authority1 = getAuthoritySample1();
        Authority authority2 = new Authority();
        assertThat(authority1).isNotEqualTo(authority2);

        authority2.setName(authority1.getName());
        assertThat(authority1).isEqualTo(authority2);

        authority2 = getAuthoritySample2();
        assertThat(authority1).isNotEqualTo(authority2);
    }

    @Test
    void hashCodeVerifier() {
        Authority authority = new Authority();
        assertThat(authority.hashCode()).isZero();

        Authority authority1 = getAuthoritySample1();
        authority.setName(authority1.getName());
        assertThat(authority).hasSameHashCodeAs(authority1);
    }


    public static Authority getAuthoritySample1() {
        return new Authority().name("name1");
    }

    public static Authority getAuthoritySample2() {
        return new Authority().name("name2");
    }

    public static Authority getAuthorityRandomSampleGenerator() {
        return new Authority().name(UUID.randomUUID().toString());
    }
}