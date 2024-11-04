package app.helipay.um.domain;

import static org.assertj.core.api.Assertions.assertThat;

import app.helipay.um.utils.TestUtil;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class AuthorityTest {

    @Test
    void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Authority.class);
        Authority authority1 = getAuthoritySample1();

        Authority authority2 = new Authority(authority1.getName());
        assertThat(authority1).isEqualTo(authority2);

        authority2 = getAuthoritySample2();
        assertThat(authority1).isNotEqualTo(authority2);
    }


    public static Authority getAuthoritySample1() {
        return new Authority("name1");
    }

    public static Authority getAuthoritySample2() {
        return new Authority("name2");
    }
}