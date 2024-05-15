package com.typology.entity.typologySystem;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public final class EnneagramTypingDisplay extends TypologySystemTyping
{
	public int coreType;
	public int wing;
	public int tritypeOrdered;
	public int overlay;
	public String instinctMain;
	public String instinctStack;
	public String exInstinctMain;
	public String exInstinctStack;
	public int tritypeUnordered;
	public String instinctStackFlow;
	public int exInstinctStackAbbreviation;
	public String exInstinctStackFlow;
}
