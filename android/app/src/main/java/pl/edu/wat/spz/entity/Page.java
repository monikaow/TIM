package pl.edu.wat.spz.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Page<T> {

    private List<T> content;

    private int totalElements;

    private boolean first;

    private boolean last;
}
