package com.didaagency.adopteunelivraison.interfaces

import com.didaagency.adopteunelivraison.utils.ClickEvents

interface RvItemClickListner<T> {
    fun onItemCLick(clickEvent: ClickEvents?, item: T?, position: Int)
}