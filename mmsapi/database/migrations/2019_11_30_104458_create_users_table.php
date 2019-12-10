<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('users', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('nom');
            $table->string('prenom');
            $table->string('telephone')->unique();
            $table->string('email')->unique()->nullable();
            $table->string('sexe');
            $table->string('date_naissance')->nullable();
            $table->string('situation_matrimoniale')->nullable();
            $table->string('adresse')->nullable();
            $table->boolean('prospect');
            $table->boolean('actif');
            $table->string('login')->unique();
            $table->string('password');
            $table->rememberToken();
            $table->string('userable_type');
            $table->unsignedBigInteger('userable_id');
            $table->unsignedBigInteger('commune_id');

            $table->foreign('commune_id')->references('id')->on('communes')->ondelete('cascade');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('users');
    }
}
