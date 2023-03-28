package vn.com.panda.learncardriving.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingDataDTO<T> {
    private List<T> data;
    private long totalPage;
    private long totalElements;

    public List<T> getData() {
        if(data == null) {
            data = new ArrayList<>();
        }
        return data;
    }
}
