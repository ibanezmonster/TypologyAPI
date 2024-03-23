package com.typology.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;



@Documented
@Constraint(validatedBy = IntegerValueValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOrderedTritypeValues
{
	String message() default "Invalid integer value";
    int[] allowedValues() default 
    {
    825, 852, 826, 862, 827, 872, 835, 853, 836, 863, 837, 873, 845, 854, 846, 864, 847, 874,
    925, 952, 926, 962, 927, 972, 935, 953, 936, 963, 937, 973, 945, 954, 946, 964, 947, 974,
    125, 152, 126, 162, 127, 172, 135, 153, 136, 163, 137, 173, 145, 154, 146, 164, 147, 174,
    258, 285, 259, 295, 251, 215, 268, 286, 269, 296, 261, 216, 278, 287, 279, 297, 271, 217,
    358, 385, 359, 395, 351, 315, 368, 386, 369, 396, 361, 316, 378, 387, 379, 397, 371, 317,
    458, 485, 459, 495, 451, 415, 468, 486, 469, 496, 461, 416, 478, 487, 479, 497, 471, 417,
    528, 582, 529, 592, 521, 512, 538, 583, 539, 593, 531, 513, 548, 584, 549, 594, 541, 514,
    628, 682, 629, 692, 621, 612, 638, 683, 639, 693, 631, 613, 648, 684, 649, 694, 641, 614,
    728, 782, 729, 792, 721, 712, 738, 783, 739, 793, 731, 713, 748, 784, 749, 794, 741, 714
    };
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
