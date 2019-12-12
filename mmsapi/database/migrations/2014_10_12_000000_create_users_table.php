<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

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
            $table->string('login');

            $table->string('userable_type');
            $table->unsignedBigInteger('userable_id');
            $table->unsignedBigInteger('commune_id');

            $table->unsignedBigInteger('marchand_id')->nullable(); 

            $table->foreign('marchand_id')->references('id')->on('marchands')->ondelete('cascade');

            $table->foreign('commune_id')
                ->references('id')
                ->on('communes')
                ->ondelete('cascade');
            $table->string('password');
            $table->rememberToken();
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
