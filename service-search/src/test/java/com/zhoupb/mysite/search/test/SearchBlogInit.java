package com.zhoupb.mysite.search.test;

import com.zhoupb.mysite.model.search.entity.SearchBlog;
import com.zhoupb.mysite.search.mapper.SearchBlogMapper;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchBlogInit {

//    @Autowired
//    private SqlSessionFactory sqlSessionFactory;
//
//    @Autowired
//    private SearchBlogRepository searchBlogRepository;
//
//    @Test
//    public void init() throws Exception {
//        try (
//                SqlSession sqlSession = sqlSessionFactory.openSession();
//                Cursor<SearchBlog> cursor = sqlSession.getMapper(SearchBlogMapper.class).getAll();
//        ) {
//            cursor.forEach(i -> {
//                SearchBlog save = searchBlogRepository.save(i);
//                System.out.println(save.getTitle());
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
