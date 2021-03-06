---
layout: documentation
displayTitle: Convert To n-grams
title: Convert To n-grams
description: Convert To n-grams
usesMathJax: true
includeOperationsMenu: true
---
Converts arrays of strings to arrays of n-grams. Null values in the input arrays are ignored. Each n-gram is represented by a space-separated string of words. When the input is empty, an empty array is returned. When the input array is shorter than n (number of elements per n-gram), no n-grams are returned.

This operation is ported from Spark ML.


For a comprehensive introduction, see
<a target="_blank" href="https://spark.apache.org/docs/1.6.1/ml-features.html#n-gram">Spark documentation</a>.


For scala docs details, see
<a target="_blank" href="https://spark.apache.org/docs/1.6.1/api/scala/index.html#org.apache.spark.ml.feature.NGram">org.apache.spark.ml.feature.NGram documentation</a>.

**Since**: Seahorse 1.0.0

## Input


<table>
<thead>
<tr>
<th style="width:15%">Port</th>
<th style="width:15%">Type Qualifier</th>
<th style="width:70%">Description</th>
</tr>
</thead>
<tbody>
    <tr><td><code>0</code></td><td><code><a href="../classes/dataframe.html">DataFrame</a></code></td><td>The input <code>DataFrame</code>.</td></tr>
</tbody>
</table>


## Output


<table>
<thead>
<tr>
<th style="width:15%">Port</th>
<th style="width:15%">Type Qualifier</th>
<th style="width:70%">Description</th>
</tr>
</thead>
<tbody>
    <tr><td><code>0</code></td><td><code><a href="../classes/dataframe.html">DataFrame</a></code></td><td>The output <code>DataFrame</code>.</td></tr><tr><td><code>1</code></td><td><code><a href="../classes/transformer.html">Transformer</a></code></td><td>A <code>Transformer</code> that allows to apply the operation on other <code>DataFrames</code> using a <a href="transform.html">Transform</a>.</td></tr>
</tbody>
</table>


## Parameters


<table class="table">
<thead>
<tr>
<th style="width:15%">Name</th>
<th style="width:15%">Type</th>
<th style="width:70%">Description</th>
</tr>
</thead>
<tbody>

<tr>
<td><code>n</code></td>
<td><code><a href="../parameter_types.html#numeric">Numeric</a></code></td>
<td>The minimum n-gram length.</td>
</tr>

<tr>
<td><code>operate on</code></td>
<td><code><a href="../parameter_types.html#input-output-column-selector">InputOutputColumnSelector</a></code></td>
<td>The input and output columns for the operation.</td>
</tr>

</tbody>
</table>


{% markdown operations/examples/ConvertToNGrams.md %}
