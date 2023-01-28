package com.example.myapplication.base;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 这个类的作用就是为了把BaseActivity中的绑定和解绑的操作，都抽取出来，然后可以给到BaseFragment使用
 * 就不需要再到BaseFragment中去复制粘贴代码了
 */
public class MvpProxyImpl<V, P extends IBasePresenter> implements IMvpProxy {
    private V v;
    private P p;

    public MvpProxyImpl(V v) {
        this.v = v;
    }

    public MvpProxyImpl(P p) {
        this.p = p;
    }

    private ArrayList<P> arrayList;
    private WeakReference<V> weakReference;

    @Override
    public void bindPresenter() {
        arrayList = new ArrayList<>();
        //todo 这里实际上还可以用注解来代替掉反射，后面还需要学习注解和反射哪个更加消耗性能，从而来选择最优解
        ParameterizedType genericSuperclass = (ParameterizedType) v.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        Class actualTypeArgument = (Class) actualTypeArguments[0];
        try {
            p = (P) actualTypeArgument.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        arrayList.add(p);

        if (p != null) {
            //这里为什么用弱引用？弱引用的具体用法和使用和理解，需要沉下心来好好看看
            //一直都是在背，一点感觉都没有，包括内存泄漏问题的处理，这方面实际上是没有什么经验的。
            weakReference = new WeakReference(v);
            for (P p1 : arrayList) {
                p1.onAttach(weakReference.get());
            }
            //这里的绑定和解绑的操作，实际上就是模仿fragment和activity的生命周期的绑定和解绑
        }
    }

    @Override
    public void unBindPresenter() {
        if (weakReference == null) {
            return;
        }
        weakReference.clear();
        for (P p1 : arrayList) {
            //因为这个ArrayList是在oncreate方法里面new的，
            //如果有页面跳转的话，相当于又会new一次，每次list里面都只有一个activity
            p1.onDetach();
        }
    }

    @Override
    public Object createM() {
        ParameterizedType parameterizedType = (ParameterizedType) p.getClass().getGenericSuperclass();
        Type[] arguments = parameterizedType.getActualTypeArguments();
        Object o = null;
        try {
            o = ((Class) arguments[1]).newInstance();//实例化model
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return o;
    }

    public P getP() {
        return p;
    }
}
