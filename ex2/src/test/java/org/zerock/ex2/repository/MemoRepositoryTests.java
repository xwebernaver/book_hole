package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.ex2.entity.Memo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/*
* MemoRepository가 정상적으로 처리되고 의존성 주입에 문제가 없는지 확인 하는 코드
* [    Test worker] o.z.ex2.repository.MemoRepositoryTests   : Started MemoRepositoryTests in 3.126 seconds (JVM running for 4.395)
com.sun.proxy.$Proxy108
* [ionShutdownHook] j.LocalContainerEntityManagerFactoryBean
 * */

@SpringBootTest
public class MemoRepositoryTests {
    @Autowired
    MemoRepository memoRepository;
/*
*   @Test
    public void testClass(){
        System.out.println("#################");
        System.out.println(memoRepository.getClass().getName());
        System.out.println("#################");
    }

    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect(){
        // 데이터베이스에 존재하는 mno
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);
        System.out.println("---------------------------------------");
        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Test
    public void testSelect2(){
        Long mno = 100L;
        Memo memo = memoRepository.getOne(mno);     // getOne(ID) is deprecated  더이상 사용되지 않는단다.
        System.out.println("---------------------------------------");
        System.out.println(memo);
    }
    */

    @Test
    public void testUpdate(){
        Memo memo = Memo.builder().mno(99L).memoText("Update Text 99").build();
        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void testDelete(){
        Long mno = 99L;

        memoRepository.deleteById(mno);
    }


    @Test
    public void testPageDefault(){
        Pageable pageable = PageRequest.of(5,10);

        Page<Memo> result = (Page<Memo>) memoRepository.findAll(pageable);

        System.out.println(result);

        System.out.println("----------------------------");

        System.out.println("Total Page : " + result.getTotalPages());   // 총 몇페이지

        System.out.println("Total Count : " + result.getTotalElements()); // 전체 개수

        System.out.println("Page Number : " + result.getNumber());  // 현재 페이지 번호

        System.out.println("Page Size : " + result.getSize());  // 페이지당 데이터 개수

        System.out.println("has next page? " + result.hasNext());  // 다음 페이지 존재 여부

        System.out.println("first page? " + result.isFirst());      // 시작 페이지(0) 여부

        System.out.println("----------------------------");

        for (Memo memo : result.getContent()){
            System.out.println(memo);
        }

    }

    @Test
    public void testSort(){
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);        // and를 이용한 연결

        //Pageable pageable = PageRequest.of(0,10, sort1);              // 한가지 정렬
        Pageable pageable = PageRequest.of(0,10, sortAll);      // 두가지 정렬
        Page<Memo> result = memoRepository.findAll(pageable);
/* #
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
 */
        result.get().forEach(System.out::println);
    }


    @Test
    public void testQueryMethods(){
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for(Memo memo : list){
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethodWithPageable(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(10l, 50l, pageable);
        result.get().forEach(System.out::println);      //result.get().forEach(memo -> System.out.println(memo));
    }

}
